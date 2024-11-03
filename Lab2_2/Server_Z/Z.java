package Server_Z;

import java.net.*;

import Handlers.ClientHandler2;
public class Z {    
    public static void main(String... args) {
        try (ServerSocket server = new ServerSocket(7000)) {
            System.out.println("Server Z is listening on port: " + server.getLocalPort());
            while (true) {
                try {
                    Socket clientX = server.accept();
                    Socket clientY = server.accept();
                    ClientHandler2 readHandlerX = new ClientHandler2(clientX);
                    ClientHandler2 readHandlerY = new ClientHandler2(clientY);
                    Thread t1 = new Thread(readHandlerX);
                    Thread t2 = new Thread(readHandlerY);
                    t1.run();
                    t2.run();
                } catch (Exception ex) {
                    System.err.println("Error getting number: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in server Z: " + ex.getMessage());
        }
    }
}
