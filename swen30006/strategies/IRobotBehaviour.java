package strategies;

import automail.Storage;

public interface IRobotBehaviour {
    /**
     * startDelivery() provides the robot the opportunity to initialise state
     * in support of the other methods below.
     */
    void startDelivery();

    /**
     * @param storage refers to the pack the robot uses to deliver mail.
     * @param carryWeight is that of the same item.
     * @return When this is true, the robot is returned to the mail room.
     * The robot will always return to the mail room when the tube is empty.
     * This method allows the robot to return with items still in the tube,
     * if circumstances make this desirable.
     */
    boolean returnToMailRoom(Storage storage, int carryWeight);

    /**
     * @param priority is that of the priority mail item which just arrived.
     * @param carryWeight is that of the same item.
     * The automail system broadcasts this information to all robots
     * when a new priority mail items arrives at the building.
     */
    void priorityArrival(int priority, int carryWeight);
}
