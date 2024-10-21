import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;

public class Server{
	public static void main(String...args) throws Exception{
		GreetingService server = new BasicGreetingServiceImpl();

		GreetingService stub = (GreetingService)
					UnicastRemoteObject.exportObject(server, 0);
		//Launching the receiving point
		Registry registry = LocateRegistry.createRegistry(8080);
		registry.rebind("GreetingService", stub);
	}
}
