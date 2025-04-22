package Commands;

import java.io.BufferedInputStream;

/**
 * Команда для вывода последних 13 введённых команд.
 * Этот класс реализует команду, которая отображает список последних 13 команд, выполненных пользователем.
 */
public class HistoryCommand implements Command {
    private CommandProcessor commandProcessor;
    private String result = "Команда выполнена";
    private BufferedInputStream writer;

    /**
     * Конструктор для создания объекта HistoryCommand.
     *
     * @param commandProcessor Объект, отвечающий за обработку команд
     */
    public HistoryCommand(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
        this.writer = commandProcessor.getInputStream();
    }

    /**
     * Выполняет команду вывода последних 13 введённых команд.
     * Этот метод получает из процессора команд список последних введённых команд и выводит их на экран.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        String[] commands = commandProcessor.getDeque().toArray(new String[0]);
        result = "Список последних 13 команд:\n";
        for (int i = commands.length - 1, index = 1; i >= 0; i--, index++) {
            result += (index + ". " + commands[i] + "\n");
        }
    }

    @Override
    public Object getTicket() { return null; }

    @Override
    public void response(String result) {
        this.result = result;
    }

    @Override
    public String getResponse() {
        return this.result;
    }

    /**
     * Описание команды.
     *
     * @return Описание команды, которая выводит последние 13 команд
     */
    @Override
    public String description() {
        return "Displays the last 13 entered commands";
    }
}
