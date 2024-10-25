import java.io.*;
import java.net.Socket;

public class A {
    public static void main(String[] args) {
        RandomNumberService number = new RNServiceImpl();
        while(true) {
            try(Socket socketToX = new Socket("localhost", 5000)) {
                BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(socketToX.getOutputStream()));
                bos.write(number.generateRandomNumber());
                bos.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to X: "+ex.getMessage());
            }
        }
    }
}