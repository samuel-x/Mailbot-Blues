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
     * Add an item to the tube
     * @param item The item being added
     * @throws StorageFullException thrown if an item is added which exceeds the capacity
     */
    void add(MailItem item) throws StorageFullException;

    /**
     * This takes and removes an item from storage.
     * @return the next mail item to be delivered.
     */
    MailItem pop();

    /**
     * This function returns the top mail item it is holding.
     * @return the next email to be delivered.
     */
    MailItem peek();

    /**
     * This function returns the current size of the storage device.
     * @return the size of the tube
     */
    int getCurrentSize();

    /**
     * This function returns a boolean of whether the storage device is full.
     * @return whether the storage is full or not.
     */
    boolean isFull();

    /**
     * This function returns a boolean of whether the storage device is empty.
     * @return whether the
     */
    boolean isEmpty();

    /**
     * This function returns the maximum capacity of the storage device
     * @return the maximum capacity of the storage device.
     */
    int getMaxCapacity();
}
