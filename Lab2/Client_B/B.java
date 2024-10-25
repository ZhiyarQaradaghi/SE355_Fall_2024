package Client_B;
import java.io.*;
import java.net.Socket;

import Services.RNServiceImpl;
import Services.RandomNumberService;

public class B {
    public static void main(String[] args) {
        RandomNumberService number = new RNServiceImpl();
        while(true) {
            try(Socket socketToX = new Socket("localhost", 5004)) {
                BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(socketToX.getOutputStream()));
                bos.write(number.generateRandomNumber());
                bos.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to Y: "+ex.getMessage());
            }
        }
    }
}