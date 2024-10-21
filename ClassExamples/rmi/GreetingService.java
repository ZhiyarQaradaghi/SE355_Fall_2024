import java.rmi.*;

public interface GreetingService extends Remote{
	public String sayHello(String name) throws RemoteException;
	public String sayBye() throws RemoteException;
}
