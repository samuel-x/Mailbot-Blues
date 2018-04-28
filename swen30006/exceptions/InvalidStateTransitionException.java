package exceptions;

/**
 * An exception thrown when a transition from a specific state to another is invalid.
 */
public class InvalidStateTransitionException extends Exception {
    public InvalidStateTransitionException() {
    }

    public InvalidStateTransitionException(String message) {
        super(message);
    }

    public InvalidStateTransitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStateTransitionException(Throwable cause) {
        super(cause);
    }

    public <E extends Enum<E>> InvalidStateTransitionException(E currentState, E newState) {
        super(String.format("Cannot transition from '%s' to '%s'.", currentState, newState));
    }
}
