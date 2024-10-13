package Lab_1;

import java.net.*;
import java.io.*;

public class D {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            System.out.println("D accepting sockets on port 8000");
            while (true) {
                try (Socket socketFromC = serverSocket.accept()) {
                    System.out.println("Client C connected to Server D");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socketFromC.getInputStream()));
                    String randomNumber = in.readLine();
                    System.out.println("Received Random Number from C: "+randomNumber);
                try (Socket socketToA = new Socket("localhost", 5000)) {
                    Writer outToA = new OutputStreamWriter(socketToA.getOutputStream());
                    outToA.write(randomNumber + "\n");
                    outToA.flush();
                    System.out.println("Sending back random number to A");
                    }
                } catch (IOException e) {
                    System.err.println("Catch error in client: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println("Catch error in server: " + e);
        }
    }
}
