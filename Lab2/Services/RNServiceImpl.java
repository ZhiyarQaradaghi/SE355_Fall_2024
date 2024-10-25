package Services;
import java.util.Random;
public class RNServiceImpl implements RandomNumberService {
    public int generateRandomNumber() {
        Random randomNumber = new Random();
        int number = randomNumber.nextInt(19999);
        return number; 
    }
}
