package Console;


import Commands.ClientCommandProcessor;
import Network.*;

import java.io.*;
import java.nio.channels.*;
import java.nio.ByteBuffer;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.Set;


public class Client {
    private static final ClientCommandProcessor commandProcessor = new ClientCommandProcessor();
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 6133;

    public static String userInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public void execute(String[] args) {

    }

    public static void main(String[] args) {
        commandProcessor.ClientCommandPut();

        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new java.net.InetSocketAddress(SERVER_IP, SERVER_PORT));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);

            while (!socketChannel.finishConnect()) {

            }

            System.out.println("Подключено к серверу");

            // Поток для обработки ввода
            new Thread(() -> {
                while (true) {
                    System.out.print("Введите команду: ");
                    String input = userInput();
                    if (input.equals("exit")) {
                        try {
                            socketChannel.close();
                            System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    Request request = new Request(input, null);
                    try {
                        sendMessage(socketChannel, request);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            // Обработка событий ввода/вывода
            while (true) {
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (SelectionKey key : selectedKeys) {
                    if (key.isConnectable()) {
                        handleConnect(key);
                    } else if (key.isReadable()) {
                        Response response = receiveResponse((SocketChannel) key.channel());
                        System.out.println("Ответ от сервера: " + response.message());
                    } else if (key.isWritable()) {
                        handleWrite(key);
                    }
                }
                selectedKeys.clear();
            }

        } catch (IOException e) {
            System.out.println("Ошибка в подключении " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendMessage(SocketChannel channel, Request request) throws IOException {
        // Сериализация объекта Request в байтовый массив
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(request);
        }

        byte[] requestBytes = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(requestBytes.length);

        buffer.put(requestBytes);
        buffer.flip();  // Подготовка к записи в канал

        // Отправка данных
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private static Response receiveResponse(SocketChannel channel) throws IOException, ClassNotFoundException {
        // Чтение данных из канала в буфер
        ByteBuffer buffer = ByteBuffer.allocate(256);  // Увеличьте размер буфера, если нужно
        int bytesRead = channel.read(buffer);

        if (bytesRead == -1) {
            throw new IOException("Соединение закрыто сервером.");
        }

        buffer.flip();  // Подготовка к чтению

        // Извлекаем байты из буфера
        byte[] responseBytes = new byte[buffer.remaining()];
        buffer.get(responseBytes);

        // Десериализация байтов в объект Response
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(responseBytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Response) objectInputStream.readObject();
        }
    }


    private static void handleConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.finishConnect()) {
            System.out.println("Покдлючение установлено");
        }
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = channel.read(buffer);

        if (bytesRead == -1) {
            channel.close();
            System.out.println("Соединение закрыто сервером");
            return;
        }

        String response = new String(buffer.array(), 0, bytesRead);
        System.out.println("Ответ от сервера: " + response);
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        // Пишем данные в канал (пишется только, если сервер готов)
        SocketChannel channel = (SocketChannel) key.channel();
        String message = "Hello from Client!";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        channel.write(buffer);
        key.interestOps(SelectionKey.OP_READ); // После записи переключаем на чтение
    }

    private static void sendMessage(SocketChannel channel, String message) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        try (Socket socket = new Socket("localhost", 6133);
//             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
//
//            while (true) {
//                System.out.print("Введите команду: ");
//                String input = userInput();
//                Request request;
//
//                if (input.equals("exit")) {
//                    request = new Request("exit", null);
//                    out.writeObject(request);
//                    out.flush();
//                    Response response = (Response) in.readObject();
//                    System.out.println(response.message());
//                    System.exit(0);
//                }
//
//                // Парсишь команду, можешь использовать CommandProcessor, если хочешь
//                // Здесь формируем объект Request
//
//
//                Object argument = null;
//                if (commandProcessor.isClientCommand(input)) {
//                    argument = commandProcessor.executeArgumentCommand(input);
//                }
//                request = new Request(input, argument);
//
//                // Отправка запроса на сервер
//                out.writeObject(request);
//                out.flush();
//
//                // Получение ответа
//                Response response = (Response) in.readObject();
//                System.out.println(response.message());
//            }
//
//        } catch (IOException e) {
//            System.err.println("Некорректный ввод");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
