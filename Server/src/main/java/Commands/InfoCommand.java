package Commands;

import Collections.CollectionManager;

/**
 * Команда для вывода информации о коллекции.
 * Этот класс реализует команду, которая отображает информацию о коллекции, используя CollectionManager.
 */
public class InfoCommand implements Command {
    private final CollectionManager collectionManager;
    private String result;

    /**
     * Конструктор для создания объекта InfoCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией
     */
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду вывода информации о коллекции.
     * Этот метод вызывает методы getCreationTime() и collectionSize() из CollectionManager и выводит информацию на экран.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        String info = "Информация о коллекции:\nДата создания: " + collectionManager.getCreationTime().toString()
                + "\nКол-во элементов: " + collectionManager.collectionSize() + "\n";
        response(info);
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
     * @return Описание команды, которая выводит информацию о коллекции
     */
    @Override
    public String description() {
        return "Displays information about the collection";
    }
}
