package Commands;

import Collections.CollectionManager;

/**
 * Команда для очистки коллекции.
 * Этот класс реализует команду, которая удаляет все элементы из коллекции.
 */
public class ClearCommand implements Command {
    private CollectionManager collectionManager;
    private String result;

    /**
     * Конструктор для создания команды очистки коллекции.
     *
     * @param collectionManager Менеджер коллекции, которая будет очищена
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду очистки коллекции.
     * Удаляет все элементы из коллекции и выводит сообщение о завершении операции.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        collectionManager.getQueue().clear();
        response("Коллекция очищена");
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
     * @return Описание команды, которая очищает коллекцию
     */
    @Override
    public String description() {
        return "Clears collection";
    }
}
