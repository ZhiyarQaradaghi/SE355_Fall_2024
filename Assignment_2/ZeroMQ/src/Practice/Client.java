package src.Practice;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.*;

public class Client {
    public static void main(String...args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.PUSH);
            socket.connect("tcp://127.0.0.1:5555");
            //socket.bind("tcp://*:5555");

            int counter=0;
            while(!Thread.currentThread().isInterrupted()) {
                String request = "Hello " + counter++;
                System.out.println("Sending " + request);
                socket.send(request.getBytes(ZMQ.CHARSET), 0);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}