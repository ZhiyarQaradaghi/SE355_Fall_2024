package Client_B;

import java.io.*;
import java.net.Socket;
import Services.RandomServiceImpl;
import Services.RandomService;
import Services.ClockService;
import Services.RandomNumberResponse;

public class B implements Serializable{
    private static final long serialVersionUID = 1L;
    public int randomNumber;
    public int randomDelay;
    public String message;
    public ClockService clockObject;
    public B(int clock) {
        this.clockObject = new ClockService(clock);
    }
    public static void main(String[] args) {
        RandomService numberService = new RandomServiceImpl();
        B obj1 = new B(0);
        while (true) {
            RandomNumberResponse response = numberService.generateRandom();
            obj1.randomNumber = response.getNumber();
            obj1.randomDelay = response.getDelay();
            obj1.clockObject.clock++;
            obj1.message = "B:" + obj1.randomNumber; 
            try (Socket socketToX = new Socket("localhost", 5005);
                 ObjectOutputStream oos = new ObjectOutputStream(socketToX.getOutputStream())) {
                oos.writeObject(obj1);
                System.out.println("Sending number " + obj1.randomNumber + " from B to X");
                oos.flush();
            } catch (Exception ex) {
                System.err.println("Error sending socket to X: " + ex.getMessage());
            }

            try (Socket socketToY = new Socket("localhost", 5006);
                ObjectOutputStream oos = new ObjectOutputStream(socketToY.getOutputStream())) {
                oos.writeObject(obj1);  
                System.out.println("Sending number " + obj1.randomNumber + " from B to Y");
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
