package automail;

import exceptions.StorageFullException;

/**
 * This interface interacts with specified Robots and StorageItems to provide a common set of methods that can be used
 * regardless of data structure or algorithms used within their respective classes.
 * This indirection improves cohesion and removes the coupling between StorageTube and Robot, while also allowing
 * for future extensibility on alternative Robot/StorageTube configurations.
 */
public interface Storage {

    /**
     * This adds an item to storage.
     */
    void add(MailItem item) throws StorageFullException;

    /**
     * This takes and removes an item from storage.
     */
    MailItem pop();

    /**
     * This function returns the top mail item it is holding.
     */
    MailItem peek();

    /**
     * This function returns the current size of the storage device.
     */
    int getCurrentSize();

    /**
     * This function returns a boolean of whether the storage device is full.
     */
    boolean isFull();

    /**
     * This function returns a boolean of whether the storage device is empty.
     */
    boolean isEmpty();

    /**
     * This function returns the maximum capacity of the storage device
     * @return
     */
    int getMaxCapacity();
}
