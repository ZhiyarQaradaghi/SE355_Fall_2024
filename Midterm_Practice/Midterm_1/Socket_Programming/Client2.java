package Midterm_Practice.Midterm_1.Socket_Programming;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client2 {
    private static List<String> inbox = new ArrayList<>();

    public static void main(String... args) {
        try (Socket socket = new Socket("localhost", 6000);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner sc = new Scanner(System.in)) {

            String message;
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Show inbox");
                System.out.println("2. Send a message");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                String choice = sc.nextLine();

                switch (choice) {
                    case "1":
                        showInbox();
                        break;
                    case "2":
                        System.out.print("Enter your message: ");
                        message = sc.nextLine();
                        bw.write(message);
                        bw.newLine();
                        bw.flush();
                        inbox.add("Sent: " + message);
                        String response = br.readLine();
                        inbox.add("Received: " + response);
                        break;
                    case "3":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in Client 2: " + e.getMessage());
        }
    }

    private static void showInbox() {
        System.out.println("Inbox:");
        for (String msg : inbox) {
            System.out.println(msg);
        }
    }
}
