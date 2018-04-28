package exceptions;

/**
 * This exception is thrown when an invalid combination of robots is read from properties (i.e. weak and weak)
 */
public class InvalidRobotConfigException extends Exception {
    public InvalidRobotConfigException() {
        super("Invalid combination of robots! This configuration has no way to deliver heavy parcels.");
    }
}
