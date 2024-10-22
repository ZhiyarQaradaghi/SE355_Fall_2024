import java.io.*;
import java.net.*;

public class Client2 {
    public static void main(String[] args) {
        try(Socket client2 = new Socket("locahost", 5000)) { 
        BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(client2.getOutputStream()));
        SayByeRequest sw = new SayByeRequest();
        sw.name = "Mary";
        bos.write(sw.name);
        bos.flush(); 
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}

