package Validaters;

import Collections.Person;
import Tools.Validation;

/**
 * Класс для валидации объекта Person.
 * Проверяет корректность даты рождения, роста и веса.
 */
public class PersonValidator implements Validation {
    private final java.time.ZonedDateTime birthday;
    private final Long height;
    private final int weight;

    /**
     * Конструктор класса PersonValidator.
     *
     * @param birthday Дата рождения, которую нужно проверить.
     * @param height Рост, который нужно проверить.
     * @param weight Вес, который нужно проверить.
     */
    public PersonValidator(java.time.ZonedDateTime birthday, Long height, int weight) {
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Метод для валидации даты рождения.
     * Проверяет, что дата рождения не равна null.
     *
     * @param birthday Дата рождения для проверки.
     * @return true, если дата рождения валидна (не null), иначе false.
     */
    public boolean validateBirthday(java.time.ZonedDateTime birthday) {
        return birthday != null;
    }

    /**
     * Метод для валидации роста.
     * Проверяет, что рост больше 0 и не равен null.
     *
     * @param height Рост для проверки.
     * @return true, если рост валиден (не null и > 0), иначе false.
     */
    public boolean validateHeight(Long height) {
        return height != null && height > 0;
    }

    /**
     * Метод для валидации веса.
     * Проверяет, что вес больше 0.
     *
     * @param weight Вес для проверки.
     * @return true, если вес валиден (> 0), иначе false.
     */
    public boolean validateWeight(int weight) {
        return weight > 0;
    }

    /**
     * Метод для валидации всей информации о человеке (дата рождения, рост, вес).
     *
     * @return true, если все данные валидны, иначе false.
     */
    @Override
    public boolean validate() {
        return validateBirthday(birthday) && validateHeight(height) && validateWeight(weight);
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректных данных.
     *
     * @return Сообщение об ошибке, соответствующее невалидному полю.
     */
    @Override
    public String getErrorMessage() {
        if (birthday == null) return "Дата рождения не может быть null";
        if (height == null || height <= 0) return "Рост не может быть null или <= 0";
        if (weight <= 0) return "Вес не может быть <= 0";
        return "Неизвестная ошибка";
    }

    /**
     * Метод, который вызывает исключение, если валидация не прошла.
     *
     * @throws IllegalStateException Если данные невалидны, выбрасывается исключение с сообщением об ошибке.
     */
    public void validateOrThrow() {
        if (!validate()) {
            throw new IllegalStateException(getErrorMessage());
        }
    }
}
