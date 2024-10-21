import java.rmi.*;
import java.rmi.registry.*;

public class Client1{
	public static void main(String...args) throws Exception{
		Registry registry = LocateRegistry.getRegistry("localhost", 8080);
		GreetingService server = (GreetingService) registry
			.lookup("GreetingService");

		String response = server.sayHello("Ahmed");
		System.out.println(response);

	}
}
