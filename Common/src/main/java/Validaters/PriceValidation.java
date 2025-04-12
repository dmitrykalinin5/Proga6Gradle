package Validaters;

import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации цены.
 * Проверяет, что цена больше 0.
 */
public class PriceValidation implements Validation {
    private Long price;
    private final Scanner scanner = new Scanner(System.in);

    public PriceValidation(String userInput) {
        validation(userInput);
    }

    /**
     * Метод для валидации цены.
     * Запрашивает у пользователя ввод и проверяет, что цена больше 0.
     *
     * @return Валидированное значение цены.
     */
    public Long validation(String input) {
        while (true) {
            try {
                this.price = Long.parseLong(input);
                if (!validate()) {
                    System.out.println("Цена должна быть больше 0, попробуйте еще раз");
                    input = scanner.nextLine().trim();
                    continue;
                }
                return this.price;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения валидированного значения цены.
     *
     * @return Цена, введенная пользователем.
     */
    public Long getPrice() { return price; }

    /**
     * Метод для валидации цены.
     * Проверяет, что цена больше 0.
     *
     * @return true, если цена больше 0, иначе false.
     */
    @Override
    public boolean validate() { return price > 0; }

    /**
     * Метод для получения сообщения об ошибке при неверной цене.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() { return "Ошибка в цене"; }
}