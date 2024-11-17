package Week_9;

public class Server {
    public static void main(String[] args) {
        try(ZContext context = new ZContext()){ //when you close one context, all sockets and everything related to it will be destroyed
            ZMQ.Socket socket = context.createSOcket(SocketType.PULL);
            // no output nor input stream

            while(!Thread.currentThread().isInterrupted()) {
                byte[] reply = socket.recv(0);
                System.out.println();
            }
        }
    }
}
