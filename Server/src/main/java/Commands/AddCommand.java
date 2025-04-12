package Commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Collections.CollectionManager;
import Collections.*;
import Validaters.*;

/**
 * Команда для добавления нового элемента в коллекцию.
 * Выполняет все необходимые шаги для ввода данных пользователя,
 * валидации этих данных и создания нового объекта Ticket, который затем добавляется в коллекцию.
 */
public class AddCommand implements Command {
    private final CollectionManager collectionManager;
    private final CommandProcessor commandProcessor;
    private String result;
    private Ticket ticket;
    private final Scanner scanner = new Scanner(System.in);

    public AddCommand(CollectionManager collectionManager, CommandProcessor commandProcessor) {
        this.collectionManager = collectionManager;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Выполняет команду добавления нового билета в коллекцию.
     * Запрашивает у пользователя данные для создания объекта Ticket, валидирует их
     * и добавляет в коллекцию.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        int newId;
        if (commandProcessor.getScriptFlag()) {
            newId = collectionManager.getNextId();
        } else {
            CollectionManager collectionManager = new CollectionManager();
            collectionManager.loadFromFile();
            newId = collectionManager.getNextId();
        }

        try {
            System.out.print("Введите ваше имя: ");
            String userInput;
            if (commandProcessor.getScriptFlag()) { userInput = commandProcessor.getNextCommand().trim(); } else { userInput = scanner.nextLine(); }
            NameValidation nameValidation = new NameValidation(userInput);
            String name = nameValidation.getName();

            System.out.println("--Ввод координат--");
            System.out.print("Введите координату x: ");
            String xInput;
            if (commandProcessor.getScriptFlag()) { xInput = commandProcessor.getNextCommand().trim(); } else { xInput = scanner.nextLine(); }
            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(xInput);
            int x = xCoordinateValidation.getX();

            String yInput;
            System.out.print("Введите координату y: ");
            if (commandProcessor.getScriptFlag()) { yInput = commandProcessor.getNextCommand().trim(); } else { yInput = scanner.nextLine(); }
            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(yInput);
            double y = yCoordinateValidation.getY();
            Coordinates coordinates = new Coordinates(x, y);

            LocalDateTime date = LocalDateTime.now();

            String priceInput;
            System.out.print("Введите цену: ");
            if (commandProcessor.getScriptFlag()) { priceInput = commandProcessor.getNextCommand().trim(); } else { priceInput = scanner.nextLine(); }
            PriceValidation priceValidation = new PriceValidation(priceInput);
            Long price = priceValidation.getPrice();

            System.out.print("Введите тип билета (VIP, USUAL, CHEAP): ");
            String typeInput;
            if (commandProcessor.getScriptFlag()) { typeInput = commandProcessor.getNextCommand().trim(); } else { typeInput = scanner.nextLine(); }
            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(typeInput);
            TicketType ticketType = ticketTypeValidation.getTicketType();

            System.out.print("Введите дату рождения в формате DD.MM.YYYY: ");
            String birthdayInput;
            if (commandProcessor.getScriptFlag()) { birthdayInput = commandProcessor.getNextCommand().trim(); } else { birthdayInput = scanner.nextLine(); }
            BirthdayValidation birthdayValidation = new BirthdayValidation(birthdayInput);
            String birthdayString = birthdayValidation.getBirthday();
            LocalDate localdate = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());

            System.out.print("Введите ваш рост: ");
            String heightInput;
            if (commandProcessor.getScriptFlag()) { heightInput = commandProcessor.getNextCommand().trim(); } else { heightInput = scanner.nextLine(); }
            HeightValidation heightValidation = new HeightValidation(heightInput);
            Long height = heightValidation.getHeight();

            System.out.print("Введите ваш вес: ");
            String weightInput;
            if (commandProcessor.getScriptFlag()) { weightInput = commandProcessor.getNextCommand().trim(); } else { weightInput = scanner.nextLine(); }
            WeightValidation weightValidation = new WeightValidation(weightInput);
            int weight = weightValidation.getWeight();

            System.out.print("Введите координаты вашей локации через пробел (x y z): ");
            String locationInput;
            if (commandProcessor.getScriptFlag()) { locationInput = commandProcessor.getNextCommand().trim(); } else { locationInput = scanner.nextLine(); }
            LocationValidation locationValidation = new LocationValidation(locationInput);
            Location location = locationValidation.getLocation();

            Person person = new Person(birthday, height, weight, location);
            this.ticket = new Ticket(newId, name, coordinates, date, price, ticketType, person);

            if (commandProcessor.getScriptFlag()) {this.collectionManager.getQueue().add(ticket);}
        } catch (NumberFormatException e) {
            response("Некорректный ввод: " + e.getMessage());
        }
    }

    @Override
    public Ticket getTicket() {
        return this.ticket;
    }

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
     * @return Описание команды, которая добавляет элемент в коллекцию
     */
    @Override
    public String description() { return "Adds element to collection"; }
}
