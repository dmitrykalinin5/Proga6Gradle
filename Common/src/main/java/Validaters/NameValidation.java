package Validaters;

import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации имени.
 * Проверяет, является ли введенное имя валидным (не пустое и не null).
 */
public class NameValidation implements Validation {
    private String name;
    private final Scanner scanner = new Scanner(System.in);

    public NameValidation(String userInput) {
        validation(userInput);
    }

    public String validation(String input) {
        while (true) {
            try {
                this.name = input;
                // Проверяем, что имя не пустое
                if (!validate()) {
                    System.out.println("Неверный формат ввода, попробуйте еще раз");
                    input = scanner.nextLine().trim();
                    continue;
                }
                return this.name;
            } catch (Exception e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения имени.
     *
     * @return Введенное имя.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean validate() {
        return name != null && !name.isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Ошибка в ID";
    }
}