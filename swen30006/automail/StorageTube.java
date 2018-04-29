package automail;

import exceptions.StorageFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube implements Storage {

    private int maxCapacity;
    public Stack<MailItem> tube = new Stack<>();

    /**
     * Constructor for the storage tube
     * @param maxCapacity the max capacity of the tube.
     */
    public StorageTube(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean isEmpty(){
        return this.tube.isEmpty();
    }

    @Override
    public MailItem peek() {
        return this.tube.peek();
    }

    @Override
    public void add(MailItem item) throws StorageFullException {
        if(tube.size() < maxCapacity){
            tube.add(item);
        } else {
            throw new StorageFullException();
        }
    }

    @Override
    public int getCurrentSize(){
        return this.tube.size();
    }

    @Override
    public MailItem pop(){
        return this.tube.pop();
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    @Override
    public boolean isFull() {
        return this.tube.size() >= this.maxCapacity;
    }
}
