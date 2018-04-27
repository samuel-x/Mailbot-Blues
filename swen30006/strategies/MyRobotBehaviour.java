package strategies;

import automail.MailItem;
import automail.StorageTube;

public class MyRobotBehaviour implements IRobotBehaviour {
	
	private final boolean strong;
	private int newPriorityLevel; // Used if we are notified that a priority item has arrived.
		
	public MyRobotBehaviour(boolean strong) {
		this.strong = strong;
        this.newPriorityLevel = 0;
	}
	
	public void startDelivery() {
        this.newPriorityLevel = 0;
	}
	
	@Override
    public void priorityArrival(int priority, int weight) {
		// Only the strong robot will deliver priority items so weight of no interest
    	if (priority > this.newPriorityLevel) {
            this.newPriorityLevel = priority;
		}
    }
 
	private int tubePriority(StorageTube tube) {  // Assumes at least one item in tube TODO: Why Peter? Don't assume.
		MailItem item = tube.peek();
		return item.getPriorityLevel();
	}
	
	@Override
	public boolean returnToMailRoom(StorageTube tube) {
		if (tube.isEmpty()) {
			return false; // Empty tube means we are returning anyway

		} else {
			// Return true for the strong robot if the one waiting is higher priority than the one we have
			// Assumes that the one at the top of the tube has the highest priority
			return this.strong && this.newPriorityLevel > tubePriority(tube);
		}
	}
	
}
