package org.example.app;

import java.util.*;
import java.io.*;
import java.net.*;

public class Main {
    public static int[] vectorClock = {0,0,0,0,0,0};
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence");
        String sentence = scanner.nextLine();
        scanner.close();

        String[] messages = sentence.split("\\s+");
        vectorClock[0]++;
        System.out.println("Messages: "+Arrays.toString(messages)+"\nVector Clock v1++: "+Arrays.toString(vectorClock));


        int[] randomPorts = {8081, 8082, 8083, 8084, 8085};
        Random random = new Random();
        
         for (String message : messages) {
            int randomPort = randomPorts[random.nextInt(randomPorts.length)]; 

            try (Socket socket = new Socket("localhost", randomPort)) {
                vectorClock[0]++;
                System.out.println("Vector Clock v1++: " + Arrays.toString(vectorClock));

                WordWithClock msg = WordWithClock.newBuilder()
                        .setWord(message)
                        .addAllVectorClock(Arrays.asList(Arrays.stream(vectorClock).boxed().toArray(Integer[]::new)))
                        .build();

                OutputStream out = socket.getOutputStream();
                msg.writeDelimitedTo(out);
                out.flush();
                System.out.println("Sent \"" + message + "\" to port " + randomPort);
            } catch (Exception e) {
                System.err.println("Failed to send \"" + message + "\" to port " + randomPort + ": " + e.getMessage());
            }
        }
    }
}
