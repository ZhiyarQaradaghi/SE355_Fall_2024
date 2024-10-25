package Week_6;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = new ServerSocket();
        while(true) {
            Socket client = server.accept();
            OutputStream out = client.getOutputStream();

            SayHelloRequest msg = SayHelloRequest.newBuilder()
                .setFirstName("Maya")
                .setLastName("Tahir")
                .setAge(12)
                .build();

                msg.writeDelimitedTo(out); // this is better because of marshalling  
                // writeTo(out);
                out.flush();
        }
    }
}
