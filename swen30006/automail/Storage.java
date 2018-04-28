package automail;

import exceptions.StorageFullException;

/**
 * This interface interacts with specified Robots and StorageIems to provide a common set of methods that can be used
 * regardless of data structure or algorithms used within their respective classes.
 * This indirection improves cohesion and removes the coupling between StorageTube and Robot, while also allowing
 * for future extensibility on alternative Robot/StorageTube configurations.
 */
public interface Storage {

    /**
     * This adds an item to storage.
     */
    void addItem(MailItem item) throws StorageFullException;

    /**
     * This takes and removes an item from storage.
     */
    MailItem takeItem();

    /**
     * This function returns the top mail item it is holding.
     */
    MailItem peek();

    /**
     * This function returns the size of the storage device.
     */
    int getSize();

    /**
     * This function returns a boolean of whether the storage device is full.
     */
    boolean isFull();

    /**
     * This function returns a boolean of whether the storage device is empty.
     */
    boolean isEmpty();

    /**
     * This function returns a boolean of whether the current item is a priority item
     */
    boolean canDeliverPriorityItem();

}
