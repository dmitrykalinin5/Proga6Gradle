package Validaters;

import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации роста.
 * Проверяет, является ли введенное значение роста валидным (больше 0).
 */
public class HeightValidation implements Validation {
    private Long Height;
    private final Scanner scanner = new Scanner(System.in);

    public HeightValidation(String userInput) {
        validation(userInput);
    }

    /**
     * Метод для валидации введенного роста.
     * Запрашивает ввод роста у пользователя, проверяет его корректность (рост должен быть больше 0).
     *
     * @return Корректное значение роста, если оно валидно.
     */
    public Long validation(String input) {
        while (true) {
            try {
                this.Height = Long.parseLong(input);
                // Проверяем, что рост больше 0
                if (!validate()) {
                    System.out.println("Рост должен быть больше 0, попробуйте еще раз");
                    input = scanner.nextLine().trim();
                    continue;
                }
                return this.Height;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения значения роста.
     *
     * @return Введенный рост.
     */
    public Long getHeight() { return Height; }

    /**
     * Метод для проверки валидности введенного роста.
     *
     * @return true, если рост валиден (больше 0), иначе false.
     */
    @Override
    public boolean validate() {
        return Height != null && Height > 0;
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректного роста.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в росте";
    }
}