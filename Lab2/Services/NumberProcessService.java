package Services;

// reference: https://www.geeksforgeeks.org/split-string-java-examples/
public class NumberProcessService {
    public void processNumber(String message) {
        String[] parts = message.split(":"); 
        String identifier = parts[0]; 
        int number = Integer.parseInt(parts[1]); 
        System.out.println("Number received from " + identifier + ": " + number);
    }
}
