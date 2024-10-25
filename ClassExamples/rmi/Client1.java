import java.rmi.*;
import java.rmi.registry.*;

public class Client1{
	public static void main(String...args) throws Exception{
		Registry register = LocateRegistry.getRegistry("localhost", 8080);
		GreetingService server = (GreetingService) register.lookup("GreetingService");

		String response = server.sayHello("John");
		System.err.println(response);
	}
}
