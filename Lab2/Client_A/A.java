package Client_A;
import java.io.*;
import java.net.Socket;

import Services.RNServiceImpl;
import Services.RandomNumberService;

public class A {
    public static void main(String[] args) {
        RandomNumberService number = new RNServiceImpl();
        while(true) {
            try(Socket socketToX = new Socket("localhost", 5005)) {
                BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(socketToX.getOutputStream()));
                int randomNumber = number.generateRandomNumber();
                bos.write(randomNumber);
                System.out.println("Sending number "+randomNumber+" to port "+socketToX.getPort());
                bos.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to X: "+ex.getMessage());
            }
        }
    }
}