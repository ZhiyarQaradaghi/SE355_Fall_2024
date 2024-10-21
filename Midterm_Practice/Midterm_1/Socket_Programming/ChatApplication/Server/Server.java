package Midterm_Practice.Midterm_1.Socket_Programming.Server;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(6000)) {
            System.out.println("Server is listening on port 6000....");

            Socket client1 = server.accept();
            System.out.println("Client 1 - " + client1.getInetAddress() + " is connected");

            Socket client2 = server.accept();
            System.out.println("Client 2 - " + client2.getInetAddress() + " is connected");

            ClientHandler handler1 = new ClientHandler(client1, client2);
            ClientHandler handler2 = new ClientHandler(client2, client1);
            Thread t1 = new Thread(handler1);
            Thread t2 = new Thread(handler2);
            t1.start();
            t2.start();
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket sender;
    private Socket receiver;

    public ClientHandler(Socket sender, Socket receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try (
            BufferedReader br = new BufferedReader(new InputStreamReader(sender.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(receiver.getOutputStream()))) {
            String message;
            while ((message = br.readLine()) != null) {
                System.out.println("Read message from " + sender.getInetAddress() + ": " + message);
                bw.write(message);
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                sender.close();
                receiver.close();
            } catch (IOException e) {
                System.err.println("Error closing sockets: " + e.getMessage());
            }
        }
    }
}
