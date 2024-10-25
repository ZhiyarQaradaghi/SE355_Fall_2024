package Midterm_Practice.Previous_Midterm;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server {
    private static Socket client1 = null;
    private static Socket client2 = null;
    private static int roundRobinCounter = 0;

    public static void main(String[] args) {
        try (ServerSocket server1 = new ServerSocket(5000);
             ServerSocket server2 = new ServerSocket(6000)) {
            while (client1 == null || client2 == null) {
                if (client1 == null) {
                    client1 = server1.accept();
                    System.out.println("Client 1 connected: " + client1.getInetAddress());
                }
                if (client2 == null) {
                    client2 = server1.accept();
                    System.out.println("Client 2 connected: " + client2.getInetAddress());
                }
            }
            new Thread(() -> handleClient1And2()).start();
            new Thread(() -> handleClient3And4(server2)).start();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void handleClient1And2() {
        try {
            while (true) {
                ObjectInputStream ois1 = new ObjectInputStream(client1.getInputStream());
                CarRecord carReq1 = (CarRecord) ois1.readObject();
                if (carReq1.price > 0) {
                    System.out.println("Car from Client 1: " + carReq1.model + ", " + carReq1.year);
                    forwardCarRecord(carReq1);
                }

                ObjectInputStream ois2 = new ObjectInputStream(client2.getInputStream());
                CarRecord carReq2 = (CarRecord) ois2.readObject();
                if (carReq2.price > 0) {
                    System.out.println("Car from Client 2: " + carReq2.model + ", " + carReq2.year);
                    forwardCarRecord(carReq2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void forwardCarRecord(CarRecord car) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                (roundRobinCounter % 2 == 0) ? client1.getOutputStream() : client2.getOutputStream());
            oos.writeObject(car);
            roundRobinCounter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient3And4(ServerSocket server2) {
        try {
            while (true) {
                Socket client3 = server2.accept();
                System.out.println("Client 3 connected: " + client3.getInetAddress());

                Socket client4 = server2.accept();
                System.out.println("Client 4 connected: " + client4.getInetAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
