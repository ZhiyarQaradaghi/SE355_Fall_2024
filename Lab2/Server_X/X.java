package Server_X;
import java.net.*;
import java.io.*;
import Handlers.ClientReader;
import Handlers.ClientWriter;

public class X {
    public int numberFromA;
    public int numberFromB;
    public static void main(String...args) {
        try(ServerSocket server = new ServerSocket(5005)) {
            X obj = new X();
            System.out.println("Listening on port: "+server.getLocalPort());
        while(true) {
            try(Socket clientA = server.accept()) {
                ClientReader readHandlerA = new ClientReader(clientA);
                Thread readThreadA = new Thread(readHandlerA);
                readThreadA.run();
                obj.numberFromA = readHandlerA.number;
                System.out.println("Recieved random number from "+clientA.getInetAddress()+"\n number: "+obj.numberFromA+"\n");
            } catch (Exception ex) {
                System.err.println("Error in client A: "+ex.getMessage());
            }
        
            try(Socket clientB = server.accept()) {
                ClientReader readHandlerB = new ClientReader(clientB);
                Thread readThreadB = new Thread(readHandlerB);
                readThreadB.run();
                obj.numberFromB = readHandlerB.number;
            } catch (Exception ex) {
                System.err.println("Error in client B: "+ex.getMessage());
            }

            try(Socket writeToZ = new Socket("localhost", 7000)) {
                // ClientWriter writeHandlerZOne = new ClientWriter(writeToZ, obj.numberFromA);
                // ClientWriter writeHandlerZTwo = new ClientWriter(writeToZ, obj.numberFromB);
                // Thread t1 = new Thread(writeHandlerZOne);
                // Thread t2 = new Thread(writeHandlerZTwo);
                // t1.run();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(writeToZ.getOutputStream()));
                // t2.run();
                bw.write(obj.numberFromA);
                bw.flush();
                System.out.println("Sending Numbers to Z ..."+obj.numberFromA);

            } catch (Exception ex) {
                System.err.println("Error writing to Z: "+ex.getMessage());
            }
        }
        } catch (Exception ex) {
            System.err.println("Error in server X: "+ex.getMessage());
        }
    }
}

