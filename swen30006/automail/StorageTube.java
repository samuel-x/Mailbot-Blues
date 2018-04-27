package automail;

// import exceptions.RobotNotInMailRoomException;
import exceptions.StorageFullException;

import java.util.Stack;

/**
 * The storage tube carried by the robot.
 */
public class StorageTube implements Storage {

    private final int MAXIMUM_CAPACITY = 4;
    private Stack<MailItem> tube;

    /**
     * Constructor for the storage tube
     */
    public StorageTube(){
        this.tube = new Stack<MailItem>();
    }

    /**
     * @return if the storage tube is full
     */
    public boolean isFull(){
        return tube.size() == MAXIMUM_CAPACITY;
    }

    /**
     * @return if the storage tube is empty
     */
    public boolean isEmpty(){
        return tube.isEmpty();
    }
    
    /**
     * @return the first item in the storage tube (without removing it)
     */
    public MailItem check() {
    	return tube.peek();
    }

    /**
     * Add an item to the tube
     * @param item The item being added
     * @throws StorageFullException thrown if an item is added which exceeds the capacity
     */
    public void addItem(MailItem item) throws StorageFullException {
        if(tube.size() < MAXIMUM_CAPACITY){
        	tube.add(item);
        } else {
            throw new StorageFullException();
        }
    }

    /** @return the size of the tube **/
    public int getSize(){
    	return tube.size();
    }
    
    /** 
     * @return the first item in the storage tube (after removing it)
     */
    public MailItem takeItem(){
        return tube.pop();
    }

    public boolean canDeliverPriorityItem() {
        return (tube.peek().hasPriority());
    }

}
