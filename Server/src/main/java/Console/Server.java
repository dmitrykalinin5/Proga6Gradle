package Console;

import Collections.CollectionManager;
import Collections.*;
import Commands.AddCommand;
import Commands.CommandProcessor;
import Commands.UpdateIdCommand;
import Network.Request;
import Network.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

/**
 * Однопоточный сервер, принимающий команды по TCP
 */
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static CollectionManager collectionManager = new CollectionManager();
    private static Deque<String> historyDeque = new ArrayDeque<>();
    private static CommandProcessor commandProcessor;
    private static final int PORT = 6133;

    public Server(CollectionManager collectionManager, Deque<String> historyDeque) {
        Server.collectionManager = collectionManager;
        Server.historyDeque = historyDeque;
    }

    public void run() throws IOException, ClassNotFoundException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new java.net.InetSocketAddress(PORT));
        System.out.println("Сервер слушает порт " + PORT);

        commandProcessor = new CommandProcessor(collectionManager, historyDeque, "server");
        commandProcessor.CommandPut();

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        logger.info("Сервер запущен");

        while (true) {
            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    // Принятие нового подключения
                    handleAccept(serverSocketChannel, selector);
                } else if (key.isReadable()) {
                    // Чтение данных от клиента
                    handleRead(key);
                }
            }
        }
    }

    public void handleAccept(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        logger.info("Клиент подключен: " + clientChannel.getRemoteAddress());
    }

    private Person mergePerson(Person incoming, Person existing) {
        if (incoming == null) return existing;

        return new Person(
                incoming.getBirthday() != null ? incoming.getBirthday() : existing.getBirthday(),
                incoming.getHeight() != null ? incoming.getHeight() : existing.getHeight(),
                incoming.getWeight() != 0 ? incoming.getWeight() : existing.getWeight(),
                incoming.getLocation() != null ? incoming.getLocation() : existing.getLocation()
        );
    }

    private void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        try {
            Request request = receiveRequest(clientChannel);

            String[] args = request.args();
            String command = args[0];
            Ticket argument = (Ticket) request.argument();
            String responseText;
            Ticket correctedTicket;

            if (argument != null) {
                if (Objects.equals(command, "add")) {
                    AddCommand add = (AddCommand) commandProcessor.getCommand("add");
                    correctedTicket = add.ServerExecute(argument);
                } else if (Objects.equals(command, "update")) {
                    UpdateIdCommand update = (UpdateIdCommand) commandProcessor.getCommand("update");
                    correctedTicket = update.ServerExecute(argument);
                } else { correctedTicket = null;}
                collectionManager.getQueue().add(correctedTicket);
                responseText = "Элемент добавлен в коллекцию";
            } else {
                responseText = commandProcessor.executeCommand(args);
            }

            Response response = new Response(responseText);
            sendResponse(clientChannel, response);
            logger.info("Ответ отправлен клиенту");

        } catch (IOException | ClassNotFoundException e) {
            logger.warn("Ошибка при обработке клиента: " + e.getMessage());
            try {
                clientChannel.close();
            } catch (IOException ex) {
                logger.error("Ошибка при закрытии канала", ex);
            }
        }
    }

    private Request receiveRequest(SocketChannel clientChannel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);  // Размер буфера, при необходимости увеличьте
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            clientChannel.close();
            throw new IOException("Соединение закрыто клиентом.");
        }

        buffer.flip();  // Переводим буфер в режим чтения

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        // Десериализация объекта Request
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Request) objectInputStream.readObject();
        }
    }

    private void sendResponse(SocketChannel clientChannel, Response response) throws IOException {
        // Сериализация объекта Response в байтовый массив
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(response);
        }

        byte[] responseBytes = byteArrayOutputStream.toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(responseBytes.length);
        buffer.put(responseBytes);
        buffer.flip();  // Подготовка к записи в канал

        // Отправка данных
        while (buffer.hasRemaining()) {
            clientChannel.write(buffer);
        }
    }

    public void executeCommand(String command) {
        commandProcessor.CommandPut();
//        return commandProcessor.executeCommand(command);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        collectionManager.loadFromFile();
        new Server(collectionManager, historyDeque).run();
    }
}
