package Validaters;

import Collections.TicketType;
import Tools.Validation;

import java.util.Scanner;

/**
 * Класс для валидации типа билета.
 * Проверяет, что введенный тип билета является допустимым значением из перечисления TicketType.
 */
public class TicketTypeValidation implements Validation {
    private TicketType ticketType;
    private final Scanner scanner = new Scanner(System.in);

    public TicketTypeValidation(String userInput) {
        validation(userInput);
    }

    public TicketType validation(String input) {
        while (true) {
            try {
                this.ticketType = TicketType.valueOf(input.toUpperCase());
                return ticketType;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат ввода, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения валидированного значения типа билета.
     *
     * @return Тип билета, введенный пользователем.
     */
    public TicketType getTicketType() {
        return ticketType;
    }

    /**
     * Метод для валидации типа билета.
     * Этот метод всегда возвращает true, так как тип билета либо валиден, либо выбрасывается исключение.
     *
     * @return true, так как тип билета всегда валиден после валидации.
     */
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * Метод для получения сообщения об ошибке.
     *
     * @return Пустая строка, так как ошибка уже обрабатывается в validation().
     */
    @Override
    public String getErrorMessage() {
        return " ";
    }
}