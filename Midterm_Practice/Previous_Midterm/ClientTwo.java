package Midterm_Practice.Previous_Midterm;

import java.io.ObjectOutputStream;
import java.net.*;

public class ClientTwo {
    public static void main(String[] args) {
        try(Socket client2 = new Socket("localhost", 5000)) {
            ObjectOutputStream oos = new ObjectOutputStream(client2.getOutputStream());
            CarRecord car = new CarRecord();
            car.make = "Mercedes";
            car.model = "C class";
            car.price = 30000;
            car.year = 2015;
            oos.writeObject(car);
            oos.flush();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
