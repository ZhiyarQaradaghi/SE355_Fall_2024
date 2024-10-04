package Server;
import java.net.*;
import java.io.*;
import java.util.*; // for Date class

public class Server {
    public static void main(String[] args) {
        // open a port, once client shows up, give him data, close port
        try(ServerSocket server = new ServerSocket(5000);) {
            while(true){
            try(Socket connection = server.accept()) { // this wont serve clients simultanously (together/parallel) but only sequentially one after another 
                // Socket connection = server.accept(); // wait for a client to show up (3 way handshake) then it will put the value into a socket object that represents the connection between client and server 
                // Date now = new Date();
                // System.out.println(now.toString()+"\n"); // current date
    
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();
                out.write(now.toString() +"\n");
                out.flush();
            } catch(Exception ex) { // now this is more optomized due to the 2nd try catch, this will close by sending a FIN request 
                System.err.println(ex);
            }
          }
        } catch (Exception ex) {
            System.err.println("Catch1: "+ex);
        } // need 2 try catch, first for the server and the 2nd for the client
    }
    
}
