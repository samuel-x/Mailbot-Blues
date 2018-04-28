package automail;

import exceptions.StorageFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube implements Storage {

    private int maxCapacity;
    public Stack<MailItem> tube = new Stack<>();;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(int maxCapacity){
        this.tube = new Stack<MailItem>();
        this.maxCapacity = maxCapacity;
    }

    /** @return if the storage tube is full */
    public boolean isFull(){
        return this.tube.size() == maxCapacity;
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
        if(tube.size() < maxCapacity){
            tube.add(item);
        } else {
            throw new StorageFullException();
        }
    }

    /** @return the size of the tube **/
    public int getCurrentSize(){
        return this.tube.size();
    }

    /** @return the first item in the storage tube (after removing it) */
    public MailItem pop(){
        return this.tube.pop();
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

}
