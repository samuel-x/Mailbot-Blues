package strategies;

import automail.BuildingSector;
import automail.Storage;

/**
 * This class represents the behaviour of the robot.
 */
public class MyRobotBehaviour implements IRobotBehaviour {
    private BuildingSector sector;
    private int newPriorityLevel; // Used if we are notified that a priority item has arrived.

    /**
     * This initialises the behaviour of the robot and defines whether the robot will behave as it is in the upper
     * or lower section of the building.
     * @param sector Section of the building the robot is in
     */
    public MyRobotBehaviour(BuildingSector sector) {
        this.sector = sector;
        this.newPriorityLevel = 0;
    }

    /**
     * This method starts the delivery of mail items
     */
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
