import java.net.*;
import java.io.*;

public class Server{
	public static void main(String...args) throws Exception{
		ServerSocket server = new ServerSocket(8080);
		while(true){
			System.out.println("Waiting for a client");
			Socket client = server.accept();
			OutputStream out = client.getOutputStream();

			System.out.println("Sending a Protobuf Object");
			SayHelloRequest msg = SayHelloRequest.newBuilder()
				.setFirstName("Ahmed")
				.setLastName("Majeed")
				.setAge(12)
				.build();
			msg.writeDelimitedTo(out);
			out.flush();
		}

	}
}
