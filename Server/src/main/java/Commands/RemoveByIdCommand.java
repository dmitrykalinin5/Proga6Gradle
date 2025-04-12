package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Команда для удаления элемента из коллекции по его идентификатору.
 * Этот класс реализует команду, которая удаляет элемент из коллекции, используя его уникальный идентификатор.
 */
public class RemoveByIdCommand implements Command {
    private CollectionManager collectionManager;
    private PriorityQueue<Ticket> queue;
    private String result;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String[] args) {
        this.queue = collectionManager.getQueue();
        boolean isRemoved = false;
        Iterator<Ticket> iterator = queue.iterator();
        int id = Integer.parseInt(args[1]);
        try {
            while (iterator.hasNext()) {
                Ticket ticket = iterator.next();
                if (ticket.getId() == id) {
                    iterator.remove();
                    isRemoved = true;
                }
            }
            if (isRemoved) {
                response("Removed " + id + " from the collection");
            } else {
                response("Could not remove " + id + " from the collection");
            }
        } catch (NumberFormatException e) {
            System.out.println("id должно быть числом");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректный ввод");
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

    @Override
    public String description() {
        return "Removes an element by its id";
    }
}
