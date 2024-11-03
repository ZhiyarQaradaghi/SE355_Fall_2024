package Client_A;

import java.io.*;
import java.net.Socket;
import Services.RandomServiceImpl;
import Services.RandomService;
import Services.RandomNumberResponse;

public class A {
    public static void main(String[] args) {
        RandomService numberService = new RandomServiceImpl();
        int clock = 0;
        while (true) {
            RandomNumberResponse response = numberService.generateRandom();
            int randomNumber = response.getNumber();
            int randomDelay = response.getDelay();
            clock++;
            String message = "A:" + randomNumber + "| clock: " + clock; 
            try (Socket socketToX = new Socket("localhost", 5005);
                 BufferedWriter bosX = new BufferedWriter(new OutputStreamWriter(socketToX.getOutputStream()))) {
                bosX.write(message + "\n");  
                System.out.println("Sending number " + randomNumber + " from A to X");
                bosX.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to X: " + ex.getMessage());
            }

            try (Socket socketToY = new Socket("localhost", 5006);
                 BufferedWriter bosY = new BufferedWriter(new OutputStreamWriter(socketToY.getOutputStream()))) {
                bosY.write(message + "\n");  
                System.out.println("Sending number " + randomNumber + " from A to Y");
                bosY.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to Y: " + ex.getMessage());
            }

            try {
                Thread.sleep(randomDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
