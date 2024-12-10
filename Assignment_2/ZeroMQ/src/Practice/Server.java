package src.Practice;
import org.zeromq.*;

public class Server {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()){
            ZMQ.Socket socket = context.createSocket(SocketType.PULL);
            socket.bind("tcp://*:5555");

            while(!Thread.currentThread().isInterrupted()) {
                byte[] reply = socket.recv(0);
                System.out.println("Server1: Received "+ new String(reply, ZMQ.CHARSET));
            }
        } catch (Exception e) {
            
        }
    }
}
