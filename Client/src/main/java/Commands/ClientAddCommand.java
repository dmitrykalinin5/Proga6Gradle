package Commands;

import Collections.*;
import Validaters.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClientAddCommand implements Command{
    private String result = "-";
    private Ticket ticket;
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void execute(String[] args) {

        try {
            System.out.print("Введите ваше имя: ");
            String userInput;
            userInput = scanner.nextLine();
            NameValidation nameValidation = new NameValidation(userInput);
            String name = nameValidation.getName();

            System.out.println("--Ввод координат--");
            System.out.print("Введите координату x: ");
            String xInput;
            xInput = scanner.nextLine();
            XCoordinateValidation xCoordinateValidation = new XCoordinateValidation(xInput);
            int x = xCoordinateValidation.getX();

            String yInput;
            System.out.print("Введите координату y: ");
            yInput = scanner.nextLine();
            YCoordinateValidation yCoordinateValidation = new YCoordinateValidation(yInput);
            double y = yCoordinateValidation.getY();
            Coordinates coordinates = new Coordinates(x, y);

            LocalDateTime date = LocalDateTime.now();

            String priceInput;
            System.out.print("Введите цену: ");
            priceInput = scanner.nextLine();
            PriceValidation priceValidation = new PriceValidation(priceInput);
            Long price = priceValidation.getPrice();

            System.out.print("Введите тип билета (VIP, USUAL, CHEAP): ");
            String typeInput;
            typeInput = scanner.nextLine();
            TicketTypeValidation ticketTypeValidation = new TicketTypeValidation(typeInput);
            TicketType ticketType = ticketTypeValidation.getTicketType();

            System.out.print("Введите дату рождения в формате DD.MM.YYYY: ");
            String birthdayInput;
            birthdayInput = scanner.nextLine();
            BirthdayValidation birthdayValidation = new BirthdayValidation(birthdayInput);
            String birthdayString = birthdayValidation.getBirthday();
            LocalDate localdate = LocalDate.parse(birthdayString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            ZonedDateTime birthday = localdate.atStartOfDay(ZoneId.systemDefault());

            System.out.print("Введите ваш рост: ");
            String heightInput;
            heightInput = scanner.nextLine();
            HeightValidation heightValidation = new HeightValidation(heightInput);
            Long height = heightValidation.getHeight();

            System.out.print("Введите ваш вес: ");
            String weightInput;
            weightInput = scanner.nextLine();
            WeightValidation weightValidation = new WeightValidation(weightInput);
            int weight = weightValidation.getWeight();

            System.out.print("Введите координаты вашей локации через пробел (x y z): ");
            String locationInput;
            locationInput = scanner.nextLine();
            LocationValidation locationValidation = new LocationValidation(locationInput);
            Location location = locationValidation.getLocation();

            Person person = new Person(birthday, height, weight, location);
            this.ticket = new Ticket(999, name, coordinates, date, price, ticketType, person);

//            if (commandProcessor.getScriptFlag()) {this.collectionManager.getQueue().add(ticket);}
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

    @Override
    public String description() {
        return "---";
    }
}
