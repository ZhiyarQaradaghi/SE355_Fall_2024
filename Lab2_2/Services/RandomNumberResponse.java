package Services;

public class RandomNumberResponse {
    private int number;
    private int delay;

    public RandomNumberResponse(int number, int delay) {
        this.number = number;
        this.delay = delay;
    }

    public int getNumber() {
        return number;
    }

    public int getDelay() {
        return delay;
    }
}
