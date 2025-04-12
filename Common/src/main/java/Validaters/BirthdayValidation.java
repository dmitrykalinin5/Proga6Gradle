package Validaters;

import Tools.Validation;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс для валидации даты рождения пользователя.
 * Проверяет, соответствует ли введенная дата корректному формату и является ли она реальной (например, проверка
 * на количество дней в месяце, високосные года и т.д.).
 */
public class BirthdayValidation implements Validation {
    private String birthday;
    private final Scanner scanner = new Scanner(System.in);

    public BirthdayValidation(String userInput) {
        validation(userInput);
    }

    public String validation(String input) {
        int[] months31 = {1, 3, 5, 7, 8, 10, 12};
        int[] months30 = {4, 6, 9, 11};
        while (true) {
            try {
                this.birthday = input;
                String[] inputSplit = this.birthday.split("\\.");
                if (inputSplit[0].length() != 2 || inputSplit[1].length() != 2 || inputSplit[2].length() != 4) {
                    System.out.println("Некорректный ввод");
                    continue;
                }
                int day = Integer.parseInt(inputSplit[0]);
                int month = Integer.parseInt(inputSplit[1]);
                int year = Integer.parseInt(inputSplit[2]);
                // Проверка корректности введенной даты (учет количества дней в месяце и високосных годов)
                if (((day >= 1 && day <= 31 && Arrays.stream(months31).anyMatch(n -> n == month))
                        || (day >= 1 && day <= 30 && Arrays.stream(months30).anyMatch(n -> n == month))
                        || (day >= 1 && day <= 29 && month == 2 && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)))
                        || (day >= 1 && day <= 28 && month == 2 && !(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)))) && year > 0) {
                    return this.birthday;
                } else {
                    System.out.println("Некорректный ввод, попробуйте еще раз");
                    input = scanner.nextLine().trim();
                }
            } catch (NullPointerException e) {
                System.out.println("Дата не может быть null, попробуйте еще раз");
                input = scanner.nextLine().trim();
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Некорректный ввод, попробуйте еще раз");
                input = scanner.nextLine().trim();
            }
        }
    }

    /**
     * Метод для получения даты рождения.
     *
     * @return Дата рождения в формате "dd.mm.yyyy".
     */
    public String getBirthday() {
        return this.birthday;
    }

    /**
     * Метод для проверки валидности введенной даты рождения.
     *
     * @return true, если дата валидна, иначе false.
     */
    @Override
    public boolean validate() {
        return birthday != null;
    }

    /**
     * Метод для получения сообщения об ошибке в случае некорректной даты рождения.
     *
     * @return Сообщение об ошибке.
     */
    @Override
    public String getErrorMessage() {
        return "Ошибка в дне рождения";
    }
}