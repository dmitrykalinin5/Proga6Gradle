package Commands;

import Collections.CollectionManager;

/**
 * Команда для вывода элемента с минимальным id из коллекции.
 * Этот класс реализует команду, которая отображает элемент из коллекции, у которого минимальное значение поля id.
 */
public class MinByIdCommand implements Command {
    private CollectionManager collectionManager;
    private String result;

    /**
     * Конструктор для создания объекта MinByIdCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией
     */
    public MinByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду вывода элемента с минимальным id.
     * Этот метод вызывает метод getByMinimumId() из CollectionManager и выводит элемент с минимальным id на экран.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        try {
            String element = collectionManager.getQueue().peek().toString();
            response("\nЭлемент с минимальным айди:\n" + element);
        } catch (NullPointerException e) {
            response("Элемент не обнаружен");
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
     * @return Описание команды, которая выводит элемент с минимальным id
     */
    @Override
    public String description() {
        return "Displays the element from collection which id is minimum";
    }
}
