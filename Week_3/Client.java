package Week_3;
import java.net.*;
import java.io.*; 

public class Client {
    public static void main(String[] args) {
        try {
            InetAddress inward = InetAddress.getByName("192.168.0.2");
            Socket socket = new Socket("time.nist.gov", 13, inward, 0); // the inward here is the source address, without it the OS will use a default source address and the 2nd is the source port, if you put 0 then the OS will do it 
            // for you as usual - this is important in production env as you have many devices in ip a and you often need to change in the source ip address

            InputStream is = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(is); // this is the solution for the force casting - Reader will only work with text
            BufferedReader reader = new BufferedReader(ir);

            StringBuilder response = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!= null) {
                // this is much faster than the first loop, the previous code you printed character by character here you store them and the B doesnt care if you processed it or not 
                response.append(line);
                response.append("\r\n");
            }
            System.out.println(response.toString());

            //  int b = 0;
             // i changed is.read() to ir.read()
            //  while((b = ir.read()) != -1 ) {
                //  System.out.println((char) b); // this will only work for UTF-8 - english, UTF-16 - arabic,kurdish,... reads 2 bytes, other UTF
                 // if the sender sends alot data, this loop is slow then at some points you wont be able to send anything back to them - to solve this we need a data structure that holds the data temporarily - we will use BufferedReader
            // }
            
            socket.close(); // it is a good idea to not keep a connection idle for a long time, if you are done with it then close it
        } catch (Exception ex) {                                        
            System.err.println(ex);
        }
    }
}
