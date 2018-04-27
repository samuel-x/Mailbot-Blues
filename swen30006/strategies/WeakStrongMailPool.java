package strategies;

import java.util.*;

import automail.Building;
import automail.MailItem;
import automail.Storage;
import exceptions.StorageFullException;

public class WeakStrongMailPool implements IMailPool{
	private LinkedList<MailItem> upper;  // weak robot will take this set
	private LinkedList<MailItem> lower;  // strong robot will take this set
	private int divider;
	private static final int MAX_WEIGHT = 2000;

	public WeakStrongMailPool(){
		// Start empty
		upper = new LinkedList<MailItem>();
		lower = new LinkedList<MailItem>();
		divider = Building.FLOORS / 2;  // Top normal floor for strong robot
	}

	private int priority(MailItem mailItem) {
		return mailItem.getPriorityLevel();
	}
	
	public void addToPool(MailItem mailItem) {
		// This doesn't attempt to put the re-add items back in time order but there will be relatively few of them,
		// from the strong robot only, and only when it is recalled with undelivered items.
		// Check whether mailItem is for strong robot
		if (mailItem.hasPriority() || mailItem.getWeight() > MAX_WEIGHT || mailItem.getDestFloor() <= divider) {
			if (mailItem.hasPriority()) {  // Add in priority order
				int priority = mailItem.getPriorityLevel();
				ListIterator<MailItem> i = lower.listIterator();
				while (i.hasNext()) {
					if (priority(i.next()) < priority) {
						i.previous();
						i.add(mailItem);
						return; // Added it - done
					}
				}
			}
			lower.addLast(mailItem); // Just add it on the end of the lower (strong robot) list
		}
		else{
			upper.addLast(mailItem); // Just add it on the end of the upper (weak robot) list
		}
	}
	
	@Override
	public void fillStorage(Storage storage, boolean strong) {
		Queue<MailItem> q = strong ? lower : upper;
		try{
			while(!storage.isFull() && !q.isEmpty()) {
				storage.addItem(q.remove());  // Could group/order by floor taking priority into account - but already better than simple
			}
		}
		catch(StorageFullException e){
			e.printStackTrace();
		}
	}

}
