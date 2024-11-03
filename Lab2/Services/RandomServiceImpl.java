package Services;

import java.util.Random;

public class RandomServiceImpl implements RandomService {
    private final Random randomNumber = new Random();

    public RandomNumberResponse generateRandom() {
        int number = randomNumber.nextInt(999);
        int delay = randomNumber.nextInt(1000); // ms
        return new RandomNumberResponse(number, delay);
    }
}
