package automail;

import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class WeakRobot extends Robot {
    private final int TUBE_CAPACITY = 4;

    /**
     * Constructs a weak robot. See Robot.java for more constructor docs.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param mailPool is the source of mail items
     * @param carryWeight the maximum weight of a single mail item that the robot is capable of carrying.
     * @param sector is what sector of the building this robot serves.
     */
    public WeakRobot(IRobotBehaviour behaviour, IMailPool mailPool, int carryWeight, BuildingSector sector) {
        super(behaviour, mailPool, carryWeight, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }

    @Override
    protected void setRoute() throws ItemTooHeavyException {
        // Pop the item from the StorageUnit
        this.deliveryItem = this.storage.pop();

        // Check if the weak robot is capable of carrying the mail item.
        if (this.deliveryItem.getWeight() > this.carryWeight) {
            throw new ItemTooHeavyException();
        }
        // Set the destination floor
        this.destinationFloor = this.deliveryItem.getDestFloor();
    }
}
