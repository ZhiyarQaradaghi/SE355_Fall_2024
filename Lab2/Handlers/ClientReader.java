package Handlers;

import java.net.*;
import java.io.*;

public class ClientReader implements Runnable {
    private Socket conn;
    private String sourceIdentifier;

    public ClientReader(Socket conn, String sourceIdentifier) {
        this.conn = conn;
        this.sourceIdentifier = sourceIdentifier; 
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String message;
            while ((message = br.readLine()) != null) {
                System.out.println("Received message in " + sourceIdentifier + ": " + message);
                forwardToZ(message); 
            }
        } catch (Exception e) {
            System.err.println("Error in reading from client: " + e.getMessage());
        }
    }

    private void forwardToZ(String message) {
        try (Socket writeToZ = new Socket("localhost", 7000);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(writeToZ.getOutputStream()))) {
            bw.write(message + "\n"); 
            bw.flush();
        } catch (Exception ex) {
            System.err.println("Error writing to Z: " + ex.getMessage());
        }
    }
}
