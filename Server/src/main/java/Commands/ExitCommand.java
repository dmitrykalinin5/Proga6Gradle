package Commands;

/**
 * Команда для завершения работы программы.
 * Этот класс реализует команду, которая завершает выполнение программы.
 */
public class ExitCommand implements Command {
    private final CommandProcessor commandProcessor;
    private String result;

    public ExitCommand(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    /**
     * Выполняет команду завершения программы.
     * Этот метод вызывает системное завершение программы с кодом 0.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        this.commandProcessor.ServerCommandPut();
        this.commandProcessor.executeCommand(new String[]{"save"});
        response("Завершение программы..");
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
     * @return Описание команды, которая завершает выполнение программы
     */
    @Override
    public String description() {
        return "Exits the program";
    }
}
