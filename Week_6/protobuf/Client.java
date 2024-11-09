import java.net.*;
import java.io.*;

public class Client{
	public static void main(String...args) throws Exception{
		Socket client = new Socket("127.0.0.1", 8080);
		InputStream in = client.getInputStream();
		SayHelloRequest msg =  SayHelloRequest.parseDelimitedFrom(in);

		System.out.println("Data");
		System.out.println(msg.getFirstName());
		System.out.println(msg.getLastName());


	}
}
