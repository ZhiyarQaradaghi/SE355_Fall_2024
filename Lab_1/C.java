package Lab_1;

import java.net.*;
import java.io.*;

public class C {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7000)) {
            System.out.println("C accepting sockets on port 7000");
            while (true) {
                try (Socket socketFromB = serverSocket.accept()) {
                    System.out.println("Client B connected to  Server C");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socketFromB.getInputStream()));
                    String randomNumber = in.readLine();
                    System.out.println("Received Random Number from B: " + randomNumber);

                try (Socket socketToD = new Socket("localhost", 8000)) {
                    Writer outToD = new OutputStreamWriter(socketToD.getOutputStream());
                    outToD.write(randomNumber + "\n");
                    outToD.flush();
                    System.out.println("Sent random number to D");
                    }
                } catch (IOException e) {
                    System.err.println("Catch error from client: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println("Catch error from server: " + e);
        }
    }
}
