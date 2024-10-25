package Server_Z;
import java.net.*;

import Handlers.ClientReader;

import java.io.*;

public class Z {
    public int number;
    public static void main(String...args) {
        try(ServerSocket server = new ServerSocket(7000)) {
            while(true) {
                try(Socket numberFromX = server.accept()) {
                    ClientReader readHandler = new ClientReader(numberFromX);
                    Thread t1 = new Thread(readHandler);
                    t1.run();
                    int randomNumber = readHandler.number;
                    System.out.println("Number from "+numberFromX.getInetAddress().getHostAddress()+" : "+randomNumber); 
                } catch (Exception ex) {
                    System.err.println("Error getting number from X: "+ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in server: "+ex.getMessage());
        }
    }
}
