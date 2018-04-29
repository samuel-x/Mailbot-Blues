package strategies;

import java.util.*;

import automail.*;
import exceptions.StorageFullException;

/**
 * This class represents the pool of mail that is generated and handles the processing of mail to different parts
 * of the building.
 */
public class WeakStrongMailPool implements IMailPool{
    private LinkedList<MailItem> upper = new LinkedList<>();  // weak robot will take this set
    private LinkedList<MailItem> lower = new LinkedList<>();  // strong robot will take this set
    private final int divider;
    private final int CARRY_WEIGHT;

    /**
     * This is the constructor for the mail pool
     * @param carryWeight the maximum carry weight of the weak robot
     */
    public WeakStrongMailPool(int carryWeight) {
        this.CARRY_WEIGHT = carryWeight;
        this.divider = Building.FLOORS / 2;  // Top normal floor for strong robot
    }

    /**
     * This returns the priority level of a specified mail item
     * @param mailItem The mail item to check the priority of
     * @return The priority of the mail item
     */
    private int priority(MailItem mailItem) {
        return mailItem.getPriorityLevel();
    }

    /**
     * This method adds a mail item to the pool of mail
     * @param mailItem the mail item being added.
     */
    public void addToPool(MailItem mailItem) {
        // This doesn't attempt to put the re-add items back in time order but there will be relatively few of them,
        // from the strong robot only, and only when it is recalled with undelivered items.
        // Check whether mailItem is for strong robot
        if (mailItem.hasPriority() || mailItem.getWeight() > CARRY_WEIGHT || mailItem.getDestFloor() <= this.divider) {
            if (mailItem.hasPriority()) {  // Add in priority order
                int priority = mailItem.getPriorityLevel();
                ListIterator<MailItem> i = this.lower.listIterator();
                while (i.hasNext()) {
                    if (priority(i.next()) < priority) {
                        i.previous();
                        i.add(mailItem);
                        return; // Added it - done
                    }
                }
            }
            this.lower.addLast(mailItem); // Just add it on the end of the lower (strong robot) list
        } else {
            this.upper.addLast(mailItem); // Just add it on the end of the upper (weak robot) list
        }
    }

    @Override
    public void fillStorage(Storage storage, BuildingSector sector) {
        Queue<MailItem> q = sector.equals(BuildingSector.LOWER) ? this.lower : this.upper;
        try {
            while (!storage.isFull() && !q.isEmpty()) {
                // Could group/order by floor taking priority into account - but already better than simple.
                storage.add(q.remove());
            }
        } catch (StorageFullException e) {
            e.printStackTrace();
        }
    }
}
