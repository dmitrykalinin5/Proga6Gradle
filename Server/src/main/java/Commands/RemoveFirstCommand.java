package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.PriorityQueue;

/**
 * Команда для удаления первого элемента из коллекции.
 * Этот класс реализует команду, которая удаляет первый элемент из коллекции.
 */
public class RemoveFirstCommand implements Command {
    private CollectionManager collectionManager;
    private String result;

    /**
     * Конструктор для создания объекта RemoveFirstCommand.
     *
     * @param collectionManager Объект, управляющий коллекцией
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду удаления первого элемента из коллекции.
     * Если коллекция не пуста, первый элемент будет удален и будет выведено сообщение об успешном удалении.
     * Если коллекция пуста, будет выведено сообщение, что нечего удалять.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        PriorityQueue<Ticket> queue = collectionManager.getQueue();
        boolean flag = false;
        if (!queue.isEmpty()) {
            queue.remove(queue.peek());
            flag = true;
        }
        if (flag) {
            response("Элемент успешно удален");
        } else {
            response("Нечего удалять! Коллекция пуста");
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
     * @return Описание команды, которая удаляет первый элемент из коллекции.
     */
    @Override
    public String description() {
        return "Removes the first element from collection";
    }
}
