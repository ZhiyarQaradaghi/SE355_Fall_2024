import java.rmi.*;
import java.rmi.registry.*;

public class Client2{
	public static void main(String...args) throws Exception{
		Registry register = LocateRegistry.getRegistry("localhost", 8080);
		GreetingService server = (GreetingService) register.lookup("GreetingService");
		String request = server.sayBye();
		System.out.println(request);
	}
}
