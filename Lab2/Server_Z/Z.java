package Server_Z;

import java.net.*;
import Handlers.ClientReader;
import Services.NumberProcessService;

public class Z {
    private static NumberProcessService processingService = new NumberProcessService();
    
    public static void main(String... args) {
        try (ServerSocket server = new ServerSocket(7000)) {
            System.out.println("Server Z is listening on port: " + server.getLocalPort());
            while (true) {
                try {
                    Socket incomingConnection = server.accept();
                    ClientReader readHandler = new ClientReader(incomingConnection, "Z"); 
                    new Thread(readHandler).start(); 
                } catch (Exception ex) {
                    System.err.println("Error getting number: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in server Z: " + ex.getMessage());
        }
    }

    public static void processNumber(String message) {
        processingService.processNumber(message);
    }
}
