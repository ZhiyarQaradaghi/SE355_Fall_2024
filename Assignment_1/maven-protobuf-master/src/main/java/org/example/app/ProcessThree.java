package org.example.app;

import java.net.ServerSocket;
import java.net.Socket;

public class ProcessThree {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(8082)) {
            while (true) {
                Socket socketFromMain = server.accept();
            }
        } catch (Exception ex) {
            System.err.println("Error receiving word: "+ex.getMessage());
        }

    }
}
