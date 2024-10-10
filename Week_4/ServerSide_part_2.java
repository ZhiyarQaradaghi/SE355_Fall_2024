package Week_4;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import Week_3.part_1.Client;

// In socket programming you cannot have multiple processes, each client to a process
// a thread is an execution path in a process - combination of modern OS and processor

// *** threadpools - i can limit threads - make sure that its no more than x threads


// lab requirements 
/**
 * Create a network with 4 nodes(A,B,C,D) - launch 4 classes in 4 jvm
 * 
 * launch A --> generate a random number then ---> send it to B ---> B recives it then forwards it to ----> C then forwards it to D ---> forwards it to A ---> A will print the number
 * A ring topology from A -> B -> C -> D -> A print number
 * 
 * 
 * 
 */
public class ServerSide_part_2 {
	public static void main(String...args){
		try(ServerSocket server = new ServerSocket(5000);) {
            while(true){
            try {
				Socket connection = server.accept();     
				ClientHandler handler = new ClientHandler(connection);
				// handler.run();
				Thread t1 = new Thread(handler); 
				t1.start();
            } catch(Exception ex) {  
                System.err.println(ex);
            }
          }
        } catch (Exception ex) {
            System.err.println("Catch1: "+ex);
        } 
	}
}

class ClientHandler implements Runnable {
	private Socket conn;
	public ClientHandler(Socket conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try {
			Writer out = new OutputStreamWriter(this.conn.getOutputStream());
			while(true){ 
			Date now = new Date();
			out.write(now.toString() +"\n");
			out.flush();
			}
		} catch (Exception e) {
			System.err.println("Catch2: "+e);
		}
	}

}