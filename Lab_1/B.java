package Lab_1;

import java.net.*;
import java.io.*;

public class B {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("B accepting sockets on port 6000");
            while (true) {
                try (Socket socketFromA = serverSocket.accept()) {
                    System.out.println("Client A connected to Server B");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socketFromA.getInputStream()));
                    String randomNumber = in.readLine();
                    System.out.println("Received Random Number from A: " + randomNumber);

                try (Socket socketToC = new Socket("localhost", 7000)) {
                    Writer outToC = new OutputStreamWriter(socketToC.getOutputStream());
                    outToC.write(randomNumber + "\n");
                    outToC.flush();
                    System.out.println("Sent the random number to C");
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
