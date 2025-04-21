package Commands;

import Collections.Ticket;

import java.util.HashMap;

public class ClientCommandProcessor {
    private HashMap<String, Command> commands = new HashMap<>();

    public void ClientCommandPut() {
        commands.put("add", new ClientAddCommand());
//        commands.put("update", new ClientAddCommand());
    }

    public Ticket execute(String[] args) {
        try {
            Command command = commands.get(args[0]);
            command.execute(args);
            return (Ticket) command.getTicket();
        } catch (NullPointerException e) {
            System.out.println("null pointer");
        }
        return null;
    }
}
