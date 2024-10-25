import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote{
    public String getInput() throws RemoteException;  
}