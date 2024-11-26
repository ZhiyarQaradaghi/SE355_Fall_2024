package org.example.app;

import java.net.*;

public class ProcessFive {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(8084)) {
            while (true) {
                Socket socketFromMain = server.accept();
            }
        } catch (Exception ex) {
            System.err.println("Error receiving word: "+ex.getMessage());
        }

    }
}
