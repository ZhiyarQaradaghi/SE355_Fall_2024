package Server_Y;

import java.net.*;
import Handlers.ClientHandler;

public class Y {
    public static void main(String... args) {
        try (ServerSocket server = new ServerSocket(5006)) {
            System.out.println("Listening on port: " + server.getLocalPort());
            while (true) {
                try {
                    Socket clientA = server.accept();
                    Socket clientB = server.accept();
                    ClientHandler readHandlerA = new ClientHandler(clientA);
                    ClientHandler readHandlerB = new ClientHandler(clientB);
                    Thread t1 = new Thread(readHandlerA);
                    Thread t2 = new Thread(readHandlerB);
                    t1.run();
                    t2.run();
                } catch (Exception ex) {
                    System.err.println("Error in client connection: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in server Y: " + ex.getMessage());
        }
    }
}