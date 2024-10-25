package Midterm_Practice.Previous_Midterm;

import java.net.*;

public class ClientFour {
    public static void main(String[] args) {
        try(Socket client4 = new Socket("localhost", 6000)) {
    
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
