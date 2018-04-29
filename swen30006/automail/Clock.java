package automail;

/** Used to keep track of time via ticks. */
public class Clock {
    /** Represents the current time */
    private static int Time = 0;

    /**
     * Returns the current time stamp.
     * @return the time stamp.
     */
    public static int Time() {
        return Time;
    }

    /** Progress time forwards one step. */
    public static void Tick() {
        Time++;
    }
}
