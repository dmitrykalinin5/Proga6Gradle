package Commands;

import Collections.CollectionManager;
import Collections.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<Long, List<Ticket>> groupedByHeight = collectionManager.getQueue().stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getPerson().getHeight()));

        // Строим строку с количеством билетов в каждой группе
        String result = groupedByHeight.entrySet().stream()
                .map(entry -> "Рост: " + entry.getKey() + " - Количество элементов: " + entry.getValue().size())
                .collect(Collectors.joining("\n"));

        // Выводим результат
        response(result);
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
