package Client_A;

import java.io.*;
import java.net.Socket;
import Services.RandomServiceImpl;
import Services.RandomService;
import Services.ClockService;
import Services.RandomNumberResponse;

public class A implements Serializable{
    private static final long serialVersionUID = 1L;
    public int randomNumber;
    public int randomDelay;
    public String message;
    public ClockService clockObject;
    public A(int clock) {
        this.clockObject = new ClockService(clock);
    }
    public static void main(String[] args) {
        RandomService numberService = new RandomServiceImpl();
        A obj1 = new A(0);
        while (true) {
            RandomNumberResponse response = numberService.generateRandom();
            obj1.randomNumber = response.getNumber();
            obj1.randomDelay = response.getDelay();
            obj1.clockObject.clock++;
            obj1.message = "A:" + obj1.randomNumber; 
            try (Socket socketToX = new Socket("localhost", 5005);
                 ObjectOutputStream oos = new ObjectOutputStream(socketToX.getOutputStream())) {
                oos.writeObject(obj1);
                System.out.println("Sending number " + obj1.randomNumber + " from A to X");
                oos.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to X: " + ex.getMessage());
            }

            try (Socket socketToY = new Socket("localhost", 5006);
                ObjectOutputStream oos = new ObjectOutputStream(socketToY.getOutputStream())) {
                oos.writeObject(obj1);  
                System.out.println("Sending number " + obj1.randomNumber + " from A to Y");
                oos.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to Y: " + ex.getMessage());
            }

            try {
                Thread.sleep(obj1.randomDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
