package Handlers;

import java.net.*;

import Client_A.A;
import Client_B.B;

import java.io.*;

public class ClientHandler2 implements Runnable, Serializable {
    private Socket conn;

    public ClientHandler2(Socket conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(conn.getInputStream())) {
            Object object = ois.readObject();
            if (object instanceof A) {
                    A receivedObject = (A) object;
                    System.out.println("Received message from " + receivedObject.getClass().getName() + ": " + receivedObject.message+ " | time: "+receivedObject.clockObject.clock);
                }

            if (object instanceof B) {
                    B receivedObject = (B) object;
                    System.out.println("Received message from " + receivedObject.getClass().getName() + ": " + receivedObject.message+ " | time: "+receivedObject.clockObject.clock);
                }
        } catch (Exception e) {
            System.err.println("Error in reading from client: " + e.getMessage());
        }
    }
}
