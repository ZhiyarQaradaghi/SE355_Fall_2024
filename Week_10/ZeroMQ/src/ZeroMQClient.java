package src;
// agenda 
/*
 - Recap
 - ZeroMQ
 - Queues 
 - Fault Tolerance 
 - Safety property
 - Liveness  property


 If client sending to server then cllient: push, server: pull
 if server sending to client then client: pull, server:push

 BOth CANNOT be push nor pull at the same time 

 use connect() and bind(), one side must be connect() the other must be bind() // more robustness on client side, client will always be available 

 bind() and connect() both need the protocol (like TCP), with port but bind doesnt need to have ip address, you can just put * (im willing to recieve messages from any client)

 When client sends data to server, data will first be added to a queue (enqueue) then from that queue zeromq is going to take that data 
 and send it to the 2nd queue on server side and when server side calls receieve it takes data from the queue (dequeue)

 connect() --> a queue will be created *NOW* - static (doesnt care about network, will create a queue either way)
 bind() ---> a queue will be created *ONLY* when there is a process connected to the bind socket *DYNAMIC*
 example:
 If the client uses bind and the server uses connect, if the server crashes then the client will not create any QUEUE because the bind will only create a queue if there is connection 


 */

 import org.zeromq.ZMQ;
import org.zeromq.ZContext;
import org.zeromq.SocketType;

public class ZeroMQClient {
    public static void main(String[] args) {
        try(ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.PUSH);
            socket.connect("tcp://127.0.0.1:8081");
            socket.connect("tcp://127.0.0.1:8082");

            int counter = 0;
            while(!Thread.currentThread().isInterrupted()) {
                String request = "Hello " + counter++;
                System.out.println("Sending " + request);
                socket.send(request.getBytes(ZMQ.CHARSET), 0);
                Thread.sleep(100);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
