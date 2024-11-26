package src;
//javac -cp lib/jeromq-0.6.0.jar src/ZeroMQServer.java
import org.zeromq.SocketType; // for client its PUSH for server its PULL
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZeroMQServer {
    public static void main(String[] args) {
        try(ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.PULL);
            socket.bind("tcp://*:8081");

            while (!Thread.currentThread().isInterrupted()) {
                byte[] reply = socket.recv(0);
                System.out.println("Server1: Received "+ new String(reply, ZMQ.CHARSET));
                Thread.sleep(1);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }
}
