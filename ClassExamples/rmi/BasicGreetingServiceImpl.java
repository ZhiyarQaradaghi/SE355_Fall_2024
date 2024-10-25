import java.rmi.*;

public class BasicGreetingServiceImpl implements GreetingService {

	@Override
	public String sayHello(String name){
		return "Hello " + name;
	}

	@Override
	public String sayBye(){
		return "Bye";
	}
}
