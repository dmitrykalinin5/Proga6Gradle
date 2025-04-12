package Commands;

/**
 * Команда для вывода справочной информации о доступных командах.
 * Этот класс реализует команду, которая выводит на экран описание всех доступных команд.
 */
public class HelpCommand implements Command {
    private String result;

    /**
     * Выполняет команду вывода справки по возможным командам.
     * Этот метод выводит список доступных команд и их описание.
     *
     * @param args Аргументы команды (не используются в данном случае)
     */
    @Override
    public void execute(String[] args) {
        response("Справка по возможным командам:\n" +
                "1. help - справка.\n" +
                "2. info - получить информацию о коллекции.\n" +
                "3. show - получить все элементы коллекции.\n" +
                "4. add {element} - добавить новый элемент в коллекцию.\n" +
                "5. update id {element} - обновить значение элемента коллекции, id которого равен заданному.\n" +
                "6. remove_by_id id - удалить элемент из коллекции по его id.\n" +
                "7. clear - очистить коллекцию.\n" +
                "8. execute_script file_name - считать и исполнить скрипт из указанного файла.\n" +
                "9. exit - завершить программу (без сохранения в файл).\n" +
                "10. remove_first - удалить первый элемент из коллекции.\n" +
                "11. remove_head - вывести первый элемент коллекции и удалить его.\n" +
                "12. history - вывести последние 13 команд без их аргументов.\n" +
                "13. remove_all_by_price (price) - удалить из коллекции все элементы, значения поля price которых равны заданному.\n" +
                "14. min_by_id - вывести любой объект из коллекции, значение поля id которого является минимальным.\n" +
                "15. group_counting_by_person - сгруппировать элементы коллекции по значению поля person, вывести количество элементов в каждой группе.\n");
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
     * @return Описание команды, которая выводит справку о доступных командах
     */
    @Override
    public String description() {
        return "Displays help information about commands";
    }
}
