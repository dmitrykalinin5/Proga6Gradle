package Commands;

import Collections.CollectionManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Collections.*;
import Validaters.*;


//import common.src.main.java.Validaters.*;

/**
 * Команда для обновления элемента коллекции по его id.
 * Этот класс реализует команду, которая позволяет пользователю обновить
 * данные элемента коллекции по заданному id.
 */
public class UpdateIdCommand implements Command {
    private int id;
    private final CollectionManager collectionManager;
    private final Deque<String> historyDeque;
    private CommandProcessor commandProcessor;
    private PriorityQueue<Ticket> queue;
    private String result;
    private Scanner scanner;
    private Ticket ticket;

    /**
     * Конструктор для создания объекта UpdateIdCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией.
     * @param historyDeque Очередь команд, предназначенная для хранения истории выполнения.
     * @param commandProcessor Обработчик команд, используемый для выполнения команд в скрипте.
     */
    public UpdateIdCommand(CollectionManager collectionManager, Deque<String> historyDeque, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.historyDeque = historyDeque;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Выполняет команду обновления элемента коллекции по его id.
     * Запрашивает у пользователя информацию для обновления элемента (имя, координаты, цену и другие параметры)
     * и обновляет указанный элемент в коллекции.
     *
     * @param args Аргументы команды. Первым аргументом должен быть id элемента для обновления.
     */
    @Override
    public void execute(String[] args) {
        try {
            id = Integer.parseInt(args[1]); // Парсим id элемента

            boolean flag = false;
            queue = collectionManager.getQueue();
            for (Ticket ticket : queue) {
                if (ticket.getId() == id) {
                    flag = true;
                }
            }

            // Цикл для получения корректного ввода
            if (flag) {
                while (true) {
                    String input;
                    if (commandProcessor.getScriptFlag()) {
                        input = commandProcessor.getNextCommand().trim(); // Ввод из скрипта
                        System.out.println(input);
                    } else {
                        System.out.println("Какой элемент вы хотите обновить? (имя, координаты, цена, тип билета, дата рождения, рост, вес, локация): ");
                        input = scanner.nextLine().trim(); // Ввод с клавиатуры
                    }
                    boolean isUpdated = update(id, input); // Попытка обновить элемент
                    if (isUpdated) {
                        break; // Если обновление прошло успешно, выходим из цикла
                    } else {
                        System.out.println("Некорректный ввод"); // Если ввод некорректный, просим повторить
                    }
                }
                response("Данные обновлены");
            } else {response("Неверный айди");}
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            response("Некорректный ввод");
        }
    }

    public boolean update(int id, String element) {
        queue = collectionManager.getQueue();
        try {
            for (Ticket ticket : queue) {
                if (ticket.getId() == id) {
                    CollectionManager c = new CollectionManager();
                    Deque<String> h = new ArrayDeque<>();
                    String userInput;
                    switch (element.toLowerCase()) {
                        case "имя":
                            System.out.print("Введите обновленное имя: ");
                            if (commandProcessor.getScriptFlag()) { userInput = commandProcessor.getNextCommand().trim(); } else { userInput = scanner.nextLine(); }
                            NameValidation nameValidation = new NameValidation(userInput);
                            String name = nameValidation.getName();
                            ticket.setName(name);
                            return true;
                        case "координаты":
                            System.out.println("--Ввод координат--");
                            System.out.print("Введите обновленную координату x: ");
                            String xInput;
                            if (commandProcessor.getScriptFlag()) { xInput = commandProcessor.getNextCommand().trim(); } else { xInput = scanner.nextLine(); }
                            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(xInput);
                            int x = xCoordinateValidation.getX();
                            String yInput;
                            System.out.print("Введите обновленную координату y: ");
                            if (commandProcessor.getScriptFlag()) { yInput = commandProcessor.getNextCommand().trim(); } else { yInput = scanner.nextLine(); }
                            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(yInput);
                            double y = yCoordinateValidation.getY();
                            Coordinates coords = new Coordinates(x, y);
                            ticket.setCoordinates(coords);
                            return true;
                        case "цена":
                            String priceInput;
                            System.out.print("Введите обновленную цену: ");
                            if (commandProcessor.getScriptFlag()) { priceInput = commandProcessor.getNextCommand().trim(); } else { priceInput = scanner.nextLine(); }
                            PriceValidation priceValidation = new PriceValidation(priceInput);
                            Long price = priceValidation.getPrice();
                            ticket.setPrice(price);
                            return true;
                        case "тип билета":
                            System.out.print("Введите обновленный тип билета (VIP, USUAL, CHEAP): ");
                            String typeInput;
                            if (commandProcessor.getScriptFlag()) { typeInput = commandProcessor.getNextCommand().trim(); } else { typeInput = scanner.nextLine(); }
                            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(typeInput);
                            TicketType ticketType = ticketTypeValidation.getTicketType();
                            ticket.setType(ticketType);
                            return true;
                        case "дата рождения":
                            System.out.print("Введите обновленную дату рождения в формате DD.MM.YYYY: ");
                            if (commandProcessor.getScriptFlag()) { userInput = commandProcessor.getNextCommand().trim(); } else { userInput = scanner.nextLine(); }
                            BirthdayValidation birthdayValidation = new BirthdayValidation(userInput);
                            String birthdayInput = birthdayValidation.getBirthday();
                            // парсинг даты из формата DD.MM.YYYY в ZonedDateTime
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            LocalDate localdate = LocalDate.parse(birthdayInput, formatter);
                            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());
                            ticket.setBirthday(birthday);
                            return true;
                        case "рост":
                            System.out.print("Введите ваш обновленный рост: ");
                            String heightInput;
                            if (commandProcessor.getScriptFlag()) { heightInput = commandProcessor.getNextCommand().trim(); } else { heightInput = scanner.nextLine(); }
                            HeightValidation heightValidation = new HeightValidation(heightInput);
                            Long height = heightValidation.getHeight();
                            ticket.setHeight(height);
                            return true;
                        case "вес":
                            System.out.print("Введите ваш обновленный вес: ");
                            String weightInput;
                            if (commandProcessor.getScriptFlag()) { weightInput = commandProcessor.getNextCommand().trim(); } else { weightInput = scanner.nextLine(); }
                            WeightValidation weightValidation = new WeightValidation(weightInput);
                            int weight = weightValidation.getWeight();
                            ticket.setWeight(weight);
                            return true;
                        case "локация":
                            System.out.print("Введите обновленные координаты вашей локации через пробел (x y z): ");
                            String locationInput;
                            if (commandProcessor.getScriptFlag()) { locationInput = commandProcessor.getNextCommand().trim(); } else { locationInput = scanner.nextLine(); }
                            LocationValidation locationValidation = new LocationValidation(locationInput);
                            Location loc = locationValidation.getLocation();
                            ticket.setLocation(loc);
                            return true;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Такого элемента не существует");
        }
        return false;
    }

    @Override
    public Object getTicket() { return null; }

    @Override
    public void response(String result) {
        this.result = result;
    }

    @Override
    public String getResponse() {
        return this.result;
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая обновляет данные элемента по его id.
     */
    @Override
    public String description() {
        return "Обновить элемент по id";
    }
}