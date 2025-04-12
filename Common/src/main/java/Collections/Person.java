package Collections;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Класс, представляющий данные о человеке.
 * Хранит информацию о дате рождения, росте, весе и локации человека.
 *
 * Поля birthday, height и weight не могут быть null и должны удовлетворять определённым ограничениям:
 * - birthday не может быть null.
 * - height не может быть null и должно быть больше 0.
 * - weight должно быть больше 0.
 * Поле location может быть null.
 */
public class Person implements Serializable {
    private ZonedDateTime birthday; //Поле не может быть null
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    private int weight; //Значение поля должно быть больше 0
    private Location location; //Поле может быть null

    /**
     * Конструктор для создания объекта Person.
     *
     * @param birthday Дата рождения (не может быть null)
     * @param height Рост (не может быть null, знач > 0)
     * @param weight Вес (знач > 0)
     * @param location Локация (x, y, z) (может быть null)
     */
    public Person(java.time.ZonedDateTime birthday, Long height, int weight, Location location) {
//        PersonValidator personvalidator = new PersonValidator(birthday, height, weight);
//        personvalidator.validateOrThrow();

        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.location = location;
    }

    /**
     * Получить дату рождения.
     * @return Дата рождения
     */
    public ZonedDateTime getBirthday() { return birthday; }

    /**
     * Получить рост.
     * @return Рост
     */
    public Long getHeight() { return height; }

    /**
     * Получить вес.
     * @return Вес
     */
    public int getWeight() { return weight; }

    /**
     * Получить локацию.
     * @return Локация, может быть null
     */
    public Location getLocation() { return location; }

    /**
     * Установить дату рождения.
     * @param birthday Дата рождения
     */
    public void setBirhday(ZonedDateTime birthday) { this.birthday = birthday; }

    /**
     * Установить рост.
     * @param height Рост
     */
    public void setHeight(Long height) { this.height = height; }

    /**
     * Установить вес.
     * @param weight Вес
     */
    public void setWeight(int weight) { this.weight = weight; }

    /**
     * Установить локацию.
     * @param location Локация, может быть null
     */
    public void setLocation(Location location) { this.location = location; }
}
