package Validaters;

import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации координаты X.
 * Запрашивает у пользователя ввод координаты X и проверяет, что ввод является числом.
 */
public class XCoordinateValidation implements Validation {
    private int x;
    private final Scanner scanner = new Scanner(System.in);

    public XCoordinateValidation(String userInput) {
        validation(userInput);
    }

    /**
     * Метод для валидации координаты X.
     * Запрашивает у пользователя ввод и проверяет, что это целое число.
     *
     * @return Валидированное значение координаты X.
     */
    public int validation(String input) {
        while (true) {
            try {
                this.x = Integer.parseInt(input);
                return this.x;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения валидированного значения координаты X.
     *
     * @return Введенная координата X.
     */
    public int getX() {
        return x;
    }

    /**
     * Метод для валидации координаты X.
     * Для координаты X всегда возвращается true, так как нет дополнительных условий для валидации.
     *
     * @return true, так как нет специфических проверок для координаты X.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Сообщение об ошибке в случае некорректного ввода координаты X.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в координате X";
    }
}