package Console;


import Collections.*;
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

        SocketChannel socketChannel = null;
        Selector selector = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            selector = Selector.open();

            // Используем новый метод connect для начального подключения
            connect(socketChannel, selector);

            while (true) {
                // Ввод команды пользователем
                System.out.print("Введите команду: ");
                String input = userInput();
                if (input.equalsIgnoreCase("exit")) {
                    socketChannel.close();
                    System.out.println("Выход.");
                    break;
                }

                String[] argsArr = input.split(" ");
                String commandName = argsArr[0];
                Ticket ticket = null;

                if (commandProcessor.hasCommand(commandName)) {
                    ticket = commandProcessor.execute(argsArr);
                }

                Request request = new Request(argsArr, ticket);

                boolean responseReceived = false;
                while (!responseReceived) {
                    try {
                        sendMessage(socketChannel, request);
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();

                        for (SelectionKey key : selectedKeys) {
                            if (key.isConnectable()) {
                                handleConnect(key);
                            } else if (key.isReadable()) {
                                Response response = receiveResponse((SocketChannel) key.channel());
                                System.out.println(response.message());
                                responseReceived = true;
                            }
                        }
                        selectedKeys.clear();
                    } catch (IOException | ClassNotFoundException e) {
                        // Ошибка чтения или соединения
                        System.out.println("Соединение потеряно: " + e.getMessage());
                        } catch (IOException | InterruptedException ex) {
                            System.out.println("Ошибка переподключения: " + ex.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка в подключении " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void connect(SocketChannel channel, Selector selector) throws IOException {

        // Метод для подключения (используется как при первом подключении, так и при переподключении)
        channel.connect(new java.net.InetSocketAddress(SERVER_IP, SERVER_PORT));

        channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);

        // Ждем завершения подключения
        while (!channel.finishConnect()) {
            // Пауза для завершения подключения
        }

        System.out.println("Подключено к серверу");
    }

    private static void sendMessage(SocketChannel channel, Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(request);
        }

        byte[] requestBytes = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(requestBytes.length);

        buffer.put(requestBytes);
        buffer.flip();

        // Отправка данных
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private static Response receiveResponse(SocketChannel channel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        int bytesRead = channel.read(buffer);

        if (bytesRead == -1) {
            throw new IOException("Соединение закрыто сервером.");
        }

        buffer.flip();

        byte[] responseBytes = new byte[buffer.remaining()];
        buffer.get(responseBytes);

        // десереал
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

}
