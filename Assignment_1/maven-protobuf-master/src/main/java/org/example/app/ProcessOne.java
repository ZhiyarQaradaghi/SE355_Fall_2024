package org.example.app;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ProcessOne {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8085)) {
            while (true) {
                Socket socketFromMain = server.accept();
                InputStream in = socketFromMain.getInputStream();
                WordWithClock msg = WordWithClock.parseDelimitedFrom(in);

                System.out.println("Received Word: " + msg.getWord());
                System.out.println("Vector Clock: " + msg.getVectorClockList());

                int[] vectorClock = msg.getVectorClockList().stream().mapToInt(i -> i).toArray();
                vectorClock[1] += 2;

                WordWithClock response = WordWithClock.newBuilder()
                        .setWord(msg.getWord())
                        .addAllVectorClock(Arrays.asList(Arrays.stream(vectorClock).boxed().toArray(Integer[]::new)))
                        .build();

                OutputStream out = socketFromMain.getOutputStream();
                response.writeDelimitedTo(out);
                out.flush();
            }
        } catch (Exception ex) {
            System.err.println("Error receiving word: " + ex.getMessage());
        }
    }
}