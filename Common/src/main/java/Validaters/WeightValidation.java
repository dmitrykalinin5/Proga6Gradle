package Validaters;

import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации веса.
 * Проверяет, что введенный вес является положительным числом.
 */
public class WeightValidation implements Validation {
    private int weight;
    private final Scanner scanner = new Scanner(System.in);

    public WeightValidation(String userInput) {
        validation(userInput);
    }

    /**
     * Метод для валидации веса.
     * Запрашивает у пользователя ввод и проверяет, что вес больше 0.
     *
     * @return Валидированное значение веса.
     */
    public int validation(String input) {
        while (true) {
            try {
                this.weight = Integer.parseInt(input);
                if (!validate()) {
                    System.out.println("Вес должен быть больше 0, попробуйте еще раз");
                    input = scanner.nextLine().trim();
                    continue;
                }
                return this.weight;
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения валидированного значения веса.
     *
     * @return Введенный вес.
     */
    public int getweight() {
        return weight;
    }

    /**
     * Метод для валидации веса.
     * Проверяет, что вес больше 0.
     *
     * @return true, если вес больше 0, иначе false.
     */
    @Override
    public boolean validate() {
        return weight > 0;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода веса.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в росте";  // Возможно, это сообщение должно быть более специфичным для веса, например, "Ошибка в весе"
    }

    public int getWeight() {
        return weight;
    }
}