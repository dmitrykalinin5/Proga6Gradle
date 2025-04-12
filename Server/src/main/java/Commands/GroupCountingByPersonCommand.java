package Commands;

import Collections.CollectionManager;
import Collections.Ticket;
import java.util.*;

/**
 * Команда для группировки билетов по росту человека (Person) и подсчета количества элементов в каждой группе.
 */
public class GroupCountingByPersonCommand implements Command {
    private CollectionManager collectionManager;
    private String result;

    /**
     * Конструктор для создания команды, которая работает с коллекцией.
     *
     * @param collectionManager Менеджер коллекции, с которой будет работать команда
     */
    public GroupCountingByPersonCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет группировку билетов по росту (из объекта Person) и выводит количество билетов в каждой группе.
     *
     * Процесс:
     * 1. Группирует билеты по полю "рост" объекта Person.
     * 2. Выводит количество билетов в каждой группе, сгруппированной по росту.
     *
     * @param args Аргументы команды (не используются в данной реализации)
     */
    @Override
    public void execute(String[] args) {
        // Группируем билеты по росту
        Map<Long, List<Ticket>> groupedByHeight = new HashMap<>();

        // Проходим по всем тикетам в коллекции и группируем их по росту
        for (Ticket ticket : collectionManager.getQueue()) {
            long height = ticket.getPerson().getHeight(); // Получаем рост из объекта Person
            groupedByHeight.computeIfAbsent(height, k -> new ArrayList<>()).add(ticket);
        }

        // Строим строку с количеством билетов в каждой группе
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Long, List<Ticket>> entry : groupedByHeight.entrySet()) {
            long height = entry.getKey();
            int count = entry.getValue().size();
            result.append("Рост: ").append(height)
                    .append(" - Количество элементов: ").append(count)
                    .append("\n");
        }

        // Выводим результат
        response(result.toString());
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
     * @return Описание команды, которая группирует билеты по росту.
     */
    @Override
    public String description() {
        return "Grouping by Person (по росту)";
    }
}
