package Console;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Server {

    public static void main(String[] args) {
        System.out.println("Everything is fine.");
        Logger logger = LogManager.getLogger(Server.class);
        logger.info("Test");
    }
}
