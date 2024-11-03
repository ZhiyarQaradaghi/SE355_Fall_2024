package Handlers;

import java.net.*;

import Client_A.A;
import Client_B.B;

import java.io.*;

public class ClientHandler implements Runnable {
    private Socket conn;

    public ClientHandler(Socket conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(conn.getInputStream());
            Object object = ois.readObject();
            if (object instanceof A) {
                    A receivedObject = (A) object;
                    System.out.println("Received message from " + receivedObject.getClass().getName());
                    try(Socket socketToZ = new Socket("localhost", 7000)) {
                        ObjectOutputStream oos = new ObjectOutputStream(socketToZ.getOutputStream());
                        receivedObject.clockObject.clock++;
                        oos.writeObject(receivedObject);
                        oos.flush();
                        System.out.println("Sent object to Z");
                    }
                }

            if (object instanceof B) {
                    B receivedObject = (B) object;
                    System.out.println("Received message from " + receivedObject.getClass().getName());
                    try(Socket socketToZ = new Socket("localhost", 7000)) {
                        ObjectOutputStream oos = new ObjectOutputStream(socketToZ.getOutputStream());
                        receivedObject.clockObject.clock++;
                        oos.writeObject(receivedObject);
                        oos.flush();
                        System.out.println("Sent object to Z");
                    }
                }
        } catch (Exception e) {
            System.err.println("Error in reading from client: " + e.getMessage());
        }
    }
}
