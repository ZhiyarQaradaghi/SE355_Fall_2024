package Week_5;
import java.rmi.*;
// need to tell about remote objects - a remote object is accessible by other classes over the network
// give the remote object to a registry (a Data structure) - a key is a string and its value is the remote object that you want to expose over the network
// Client will talk to the registry and get the object 
public class RMI {
    //my class needs to implement an interface -- this interface will extend Remote class 
    public static void main(String args[]) {
        System.out.println("works");
    }
}
