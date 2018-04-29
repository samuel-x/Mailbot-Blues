package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class BigRobot extends Robot{

    /** Total capacity of the tube. */
    private final int TUBE_CAPACITY = 6;

    /**
     * Constructs a big robot. See Robot.java for more constructor docs.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param mailPool is the source of mail items
     * @param carryWeight the maximum weight of a single mail item that the robot is capable of carrying.
     * @param sector is what sector of the building this robot serves.
     */
    public BigRobot(IRobotBehaviour behaviour, IMailPool mailPool, int carryWeight, BuildingSector sector) {
        super(behaviour, mailPool, carryWeight, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }

    @Override
    protected void setRoute() {
        // Pop the item from the StorageUnit.
        this.deliveryItem = this.storage.pop();
        // Set the destination floor.
        this.destinationFloor = this.deliveryItem.getDestFloor();
    }
}
