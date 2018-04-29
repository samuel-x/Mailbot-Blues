package strategies;

import automail.BuildingSector;
import automail.Storage;

public class MyRobotBehaviour implements IRobotBehaviour {
    private BuildingSector sector;
    private int newPriorityLevel; // Used if we are notified that a priority item has arrived.

    public MyRobotBehaviour(BuildingSector sector) {
        this.sector = sector;
        this.newPriorityLevel = 0;
    }

    public void startDelivery() {
        this.newPriorityLevel = 0;
    }

    @Override
    public void priorityArrival(int priority, int carryWeight) {
        if (this.sector.equals(BuildingSector.LOWER) && priority > this.newPriorityLevel) {
            this.newPriorityLevel = priority;
        }
    }

    @Override
    public boolean returnToMailRoom(Storage storage, int carryWeight) {
        if (storage.isEmpty()) {
            return false; // Empty tube means we are returning anyway
        } else {
            // Return true for the strong robot if the one waiting is higher priority than the one we have
            // Assumes that the one at the top of the tube has the highest priority
            return (storage.peek().getWeight() < carryWeight)
                    && this.newPriorityLevel > (storage.peek().getPriorityLevel());
        }
    }
}
