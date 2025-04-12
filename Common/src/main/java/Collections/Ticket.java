package Collections;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Класс, представляющий билет с данными о человеке, координатах, цене и типе билета.
 * Реализует интерфейс Comparable для возможности сравнения билетов по id.
 *
 * Поля id, name, coordinates, creationDate и type не могут быть null. Поле price может быть null.
 * Поле id генерируется автоматически и должно быть уникальным, а также больше 0.
 * Поле creationDate генерируется автоматически.
 * Поле price может быть null, Значение поля должно быть больше 0.
 * Поле type не может быть null.
 * Поле person может быть null.
 */
public class Ticket implements Comparable<Ticket>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; //Поле может быть null, Значение поля должно быть больше 0
    private TicketType type; //Поле не может быть null
    private Person person; //Поле может быть null

    /**
     * Конструктор для создания объекта Ticket.
     *
     * @param id Уникальный идентификатор билета (генерируется автоматически и должен быть больше 0)
     * @param name Название билета (не может быть null, не может быть пустым)
     * @param coordinates Координаты билета (не могут быть null)
     * @param creationDate Дата создания билета (не может быть null, генерируется автоматически)
     * @param price Цена билета (может быть null, но если указано, должно быть больше 0)
     * @param type Тип билета (не может быть null)
     * @param person Человек, связанный с билетом (может быть null)
     */
    public Ticket(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Long price, TicketType type, Person person) {
        this.id = id; // сделать так, чтобы было на 1 больше предыдущего id
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.type = type;
        this.person = person;
    }

    // геттеры

    /**
     * Получить уникальный идентификатор билета.
     *
     * @return Идентификатор билета
     */
    public Integer getId() { return id; }

    /**
     * Получить название билета.
     *
     * @return Название билета
     */
    public String getName() { return name; }

    /**
     * Получить координаты билета.
     *
     * @return Координаты билета
     */
    public Coordinates getCoordinates() { return coordinates; }

    /**
     * Получить дату создания билета.
     *
     * @return Дата создания билета
     */
    public java.time.LocalDateTime getCreationDate() { return creationDate; }

    /**
     * Получить цену билета.
     *
     * @return Цена билета
     */
    public Long getPrice() { return price; }

    /**
     * Получить тип билета.
     *
     * @return Тип билета
     */
    public TicketType getType() { return type; }

    /**
     * Получить информацию о человеке, связанном с билетом.
     *
     * @return Человек, связанный с билетом
     */
    public Person getPerson() { return person; }

    /**
     * Получить дату рождения человека, связанного с билетом.
     *
     * @return Дата рождения человека
     */
    public ZonedDateTime getBirthday() { return person.getBirthday(); }

    /**
     * Получить рост человека, связанного с билетом.
     *
     * @return Рост человека
     */
    public Long getHeight() { return person.getHeight(); }

    /**
     * Получить вес человека, связанного с билетом.
     *
     * @return Вес человека
     */
    public int getWeight() { return person.getWeight(); }

    /**
     * Получить локацию человека, связанного с билетом.
     *
     * @return Локация человека
     */
    public Location getLocation() { return person.getLocation(); }

    /**
     * Установить название билета.
     *
     * @param name Название билета
     */
    public void setName(String name) { this.name = name; }

    /**
     * Установить координаты билета.
     *
     * @param coordinates Координаты билета
     */
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    /**
     * Установить цену билета.
     *
     * @param price Цена билета
     */
    public void setPrice(Long price) { this.price = price; }

    /**
     * Установить тип билета.
     *
     * @param type Тип билета
     */
    public void setType(TicketType type) { this.type = type; }

    /**
     * Установить дату рождения человека, связанного с билетом.
     *
     * @param birthday Дата рождения
     */
    public void setBirthday(ZonedDateTime birthday) { person.setBirhday(birthday); }

    /**
     * Установить рост человека, связанного с билетом.
     *
     * @param height Рост человека
     */
    public void setHeight(Long height) { person.setHeight(height); }

    /**
     * Установить вес человека, связанного с билетом.
     *
     * @param weight Вес человека
     */
    public void setWeight(int weight) { person.setWeight(weight); }

    /**
     * Установить локацию человека, связанного с билетом.
     *
     * @param location Локация человека
     */
    public void setLocation(Location location) { person.setLocation(location); }

    /**
     * Сравнивает два билета по их идентификаторам.
     *
     * @param other Другой билет, с которым производится сравнение
     * @return Результат сравнения идентификаторов двух билетов
     */
    @Override
    public int compareTo(Ticket other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Возвращает строковое представление билета в формате JSON.
     *
     * @return Строковое представление билета
     */
    @Override
    public String toString() {
        return "Ticket id: " + id + "\n" +
                "name: " + name + "\n" +
                "coordinates: " + coordinates.toString() + "\n" +
                "Дата покупки: " + creationDate + "\n" +
                "Цена: " + price + "\n" +
                "Тип билета: " + type + "\n" +
                "Дата рождения: " + person.getBirthday() + "\n" +
                "Рост: " + person.getHeight() + "\n" +
                "Вес: " + person.getWeight() + "\n" +
                "Локация: " + person.getLocation() + "\n";
//        return  "Ticket{\"id\" : " + id + ", \"name\" : \"" + name + "\", \"coordinates\" : " +
//                coordinates.toString() + ", \"Дата покупки\" : "
//                + creationDate + ", \"Цена\" : " + price +
//                ", \"Тип билета\" : " + type + ", \"Дата рождения:\" : " + person.getBirthday() +
//                ", \"Рост\" : " + person.getHeight() + ", \"Вес\" : " + person.getWeight() + ", \"Локация\" : " + person.getLocation() + "}";
    }
}
