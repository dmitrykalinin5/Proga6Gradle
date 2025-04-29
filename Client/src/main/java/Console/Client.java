package Console;


import Collections.*;
import Commands.ClientCommandProcessor;
import Network.*;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.nio.channels.*;
import java.nio.ByteBuffer;
import java.net.Socket;
import java.util.*;


public class Client {
    private static final ClientCommandProcessor commandProcessor = new ClientCommandProcessor();
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 6133;
    private static Selector selector;
    private static SocketChannel socketChannel;
    private static final Queue<Request> pendingRequests = new LinkedList<>();

    public static String userInput() {
        System.out.print("Введите команду: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public void execute(String[] args) {

    }

    public static void main(String[] args) throws IOException {
        commandProcessor.ClientCommandPut();

        boolean sent = true;
        boolean responseReceived = true;

        while (true) {
            try {
                connect();
                while (true) {
                    if (sent && responseReceived) {
                        String input = userInput();

                        if (input.equalsIgnoreCase("exit")) {
                            socketChannel.close();
                            System.out.println("Выход.");
                            System.exit(0);
                        }

                        String[] argsArr = input.split(" ");
                        String commandName = argsArr[0];
                        Ticket ticket = null;

                        if (commandProcessor.hasCommand(commandName)) {
                            ticket = commandProcessor.execute(argsArr);
                        }

                        Request request = new Request(argsArr, ticket);
                        pendingRequests.add(request);
                    }

                    sent = false;
                    responseReceived = false;
                    while (!responseReceived) {
                        selector.select();

                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            if (key.isWritable()) {
                                sendMessage(socketChannel);
                                sent = true;
                            }
                            if (key.isReadable()) {
                                Response response = receiveResponse((SocketChannel) key.channel());
                                System.out.println(response.message());
                                responseReceived = true;
                            }
                        }
                    }
                }
            } catch ( IOException | ClassNotFoundException e ) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void connect() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        socketChannel.connect(new java.net.InetSocketAddress(SERVER_IP, SERVER_PORT));
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        while (!socketChannel.finishConnect()) {
            // пауза
        }

        System.out.println("Подключение установлено");
    }

    private static void sendMessage(SocketChannel channel) throws IOException {
        while (!pendingRequests.isEmpty()) {
            Request request = pendingRequests.peek();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(request);
            }

            byte[] requestBytes = byteArrayOutputStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(requestBytes);

            while (buffer.hasRemaining()) {
                int written = channel.write(buffer);
                if (written == 0) {
                    // Канал временно не может писать — выйдем и подождём следующего OP_WRITE
                    return;
                }
            }

            pendingRequests.poll();
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
}
