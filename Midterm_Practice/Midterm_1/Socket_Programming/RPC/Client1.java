import java.io.*;
import java.net.*;

public class Client1 {
    public static void main(String[] args) {
        try(Socket client1 = new Socket("locahost", 5000)) { 
        BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(client1.getOutputStream()));
        SayHelloRequest sw = new SayHelloRequest();
        sw.name = "John";
        bos.write(sw.name);
        bos.flush(); 
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}

