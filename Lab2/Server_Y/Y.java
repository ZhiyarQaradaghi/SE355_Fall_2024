package Server_Y;

import java.io.*;
import java.net.*;
import Handlers.ClientReader;

public class Y {
    public static void main(String... args) {
        try (ServerSocket server = new ServerSocket(5006)) {
            System.out.println("Listening on port: " + server.getLocalPort());
            while (true) {
                try {
                    Socket client = server.accept();
                    String sourceIdentifier = client.getInetAddress().getHostName();
                    ClientReader readHandler = new ClientReader(client, sourceIdentifier);
                    new Thread(readHandler).start();
                } catch (Exception ex) {
                    System.err.println("Error in client connection: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in server Y: " + ex.getMessage());
        }
    }
}