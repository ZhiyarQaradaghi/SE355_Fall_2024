import java.rmi.*;
import java.rmi.registry.*;

public class Client2{
	public static void main(String...args) throws Exception{
		Registry registry = LocateRegistry.getRegistry("localhost", 8080);
		GreetingService server = (GreetingService) registry
			.lookup("GreetingService");

		String response = server.sayBye();
		System.out.println(response);

	}
}
