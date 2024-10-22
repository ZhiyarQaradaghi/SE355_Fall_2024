import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(5000)) {
            Socket client1 = server.accept();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            GreetingService gs1 = new GreetingService();
            if (br1.readLine() == "Mary") {
                gs1.sayBye(br1.readLine());                
            } else if (br1.readLine() == "John") {
                gs1.sayHello(br1.readLine());
            }
        } catch(Exception ex)  {
            System.err.println("SErver error "+ex.getMessage());
        }
    }
}