package Project_1.Sockets.Clients;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import Project_1.BusinessLogic.*;

public class Client1 {
    public static void main(String[] args) {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
        ChatService cs = (ChatService) registry.lookup("ChatService");   
    }
}