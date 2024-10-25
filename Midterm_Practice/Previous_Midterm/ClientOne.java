package Midterm_Practice.Previous_Midterm;

import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ClientOne {
    public static void main(String[] args) {
        try(Socket client1 = new Socket("localhost", 5000)) {
            ObjectOutputStream oos = new ObjectOutputStream(client1.getOutputStream());
            CarRecord car = new CarRecord();
            car.make = "Toyota";
            car.model = "Corolla";
            car.price = 20000;
            car.year = 2023;
            oos.writeObject(car);
            oos.flush();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
