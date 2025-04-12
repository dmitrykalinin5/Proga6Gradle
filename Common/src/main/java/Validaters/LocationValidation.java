package Validaters;

import Collections.Location;
import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации локации.
 * Проверяет, является ли введенная локация валидной (состоящей из трех числовых значений).
 */
public class LocationValidation implements Validation {
    private Location location;
    private final Scanner scanner = new Scanner(System.in);

    public LocationValidation(String userInput) {
        validation(userInput);
    }

    public Location validation(String input) {
        while (true) {
            try {
                String[] elements = input.split(" ");
                // Преобразуем введенные строки в соответствующие типы данных
                long x = Long.parseLong(elements[0]);
                double y = Double.parseDouble(elements[1]);
                Float z = Float.parseFloat(elements[2]);
                // Создаем объект Location с введенными значениями
                this.location = new Location(x, y, z);
                return this.location;
            } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения значения локации.
     *
     * @return Введенная локация.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Метод для проверки валидности введенной локации.
     *
     * @return true, если локация валидна, иначе false.
     */
    @Override
    public boolean validate() {
        return location != null;
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректного ввода локации.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в Coordinates";
    }
}