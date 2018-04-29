package exceptions;

/**
 * An exception thrown when the robot tries to deliver more items than its tube capacity without refilling.
 */
public class ExcessiveDeliveryException extends Throwable {

    /**
     * Used when specifying the number of items that are allowed to me delivered.
     * @param maxItems is the max number of items this robot can deliver.
     */
    public ExcessiveDeliveryException(int maxItems) {
        super(String.format("Attempting to deliver more than %d items in a single trip!!", maxItems));
    }
}
