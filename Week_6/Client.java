package Week_6;

import java.io.InputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket client = new Socket();
        InputStream is = client.getInputStream();
        SayHelloRequest say = .parseDelimitedFrom(in);
    }
}
