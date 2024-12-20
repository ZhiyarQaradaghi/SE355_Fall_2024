package Server_X;

import java.io.*;
import java.net.*;
import Handlers.ClientReader;

public class X {
    public static void main(String... args) {
        try (ServerSocket server = new ServerSocket(5005)) {
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
            System.err.println("Error in server X: " + ex.getMessage());
        }
    }
}