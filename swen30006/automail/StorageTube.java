package automail;

import exceptions.StorageFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube implements Storage {

    public final int MAXIMUM_CAPACITY = 4;
    public Stack<MailItem> tube = new Stack<>();

    /** Constructor for the storage tube */
    public StorageTube(){
    }

    /** @return if the storage tube is full */
    public boolean isFull(){
        return this.tube.size() == this.MAXIMUM_CAPACITY;
    }

    /** @return if the storage tube is empty */
    public boolean isEmpty(){
        return this.tube.isEmpty();
    }
    
    /** @return the first item in the storage tube (without removing it) */
    public MailItem peek() {
        return this.tube.peek();
    }

    /**
     * Add an item to the tube
     * @param item The item being added
     * @throws StorageFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item) throws StorageFullException {
        if (this.tube.size() < this.MAXIMUM_CAPACITY) {
            this.tube.add(item);
        } else {
            throw new StorageFullException();
        }
    }

    /** @return the size of the tube **/
    public int getSize(){
        return this.tube.size();
    }

    /** @return the first item in the storage tube (after removing it) */
    public MailItem pop(){
        return this.tube.pop();
    }
}
