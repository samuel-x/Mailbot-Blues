package automail;

public class Building {
    /** The number of floors in the building */
    public static int FLOORS = -1;
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = 1;
    
    /** Represents the mailroom location */
    public static final int MAILROOM_LOCATION = 1;

    public static void init(int floors) {
        if (floors < 0) {
            throw new IllegalArgumentException("Building floors cannot be negative.");
        }
        if (FLOORS > 0) {
            throw new IllegalArgumentException("Building FLOORS cannot be initilized more than once.");
        }
        else {
            FLOORS = floors;
        }
    }
}
