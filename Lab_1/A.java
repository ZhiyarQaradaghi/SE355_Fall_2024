package Lab_1;

import java.net.*;
import java.io.*;
import java.util.*;

public class A {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("A accepting sockets on port 5000");
            Random random = new Random();
            int randomNumber = random.nextInt(1000000);
            System.out.println("Created a random number: "+randomNumber);
            try (Socket socketToB = new Socket("localhost", 6000)) {
                Writer out = new OutputStreamWriter(socketToB.getOutputStream());
                out.write(randomNumber + "\n");
                out.flush();
                System.out.println("Send random number to B");
            }
            try (Socket socketFromD = serverSocket.accept()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socketFromD.getInputStream()));
                String receivedRandomNumber = in.readLine();
                System.out.println("Received the random number back from D: " + receivedRandomNumber);
            }
        } catch (Exception e) {
            System.err.println("Catch Error in A: " + e);
        }
    }
}
