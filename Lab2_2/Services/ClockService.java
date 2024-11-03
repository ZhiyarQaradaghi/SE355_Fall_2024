package Services;

import java.io.Serializable;

public class ClockService implements Serializable {
    private static final long serialVersionUID = 1L; 
    public int clock;

    public ClockService(int clock) {
        this.clock = clock;
    }
}
