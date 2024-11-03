package Handlers;

import java.net.*;
import java.io.*;

public class ClientWriter implements Runnable {
    private Socket conn;
    private int number;

    public ClientWriter(Socket conn, int number) {
        this.conn = conn;
        this.number = number;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            bw.write(this.number + "\n"); 
            bw.flush();
            System.out.println("Sent number: " + number + " to " + conn.getInetAddress().getHostName());
        } catch (Exception e) {
            System.err.println("Error in writing to client: " + e + " connection: " + conn.getInetAddress().getHostName());
        }
    }
}
