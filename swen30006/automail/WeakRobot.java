package automail;

import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class WeakRobot extends Robot{
    private final int TUBE_CAPACITY = 4;

    public WeakRobot(IRobotBehaviour behaviour, IMailPool mailPool, int carryWeight, BuildingSector sector) {
        super(behaviour, mailPool, carryWeight, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }

    @Override
    protected void setRoute() throws ItemTooHeavyException {
        // Pop the item from the StorageUnit
        this.deliveryItem = this.storage.pop();
        if (this.deliveryItem.getWeight() > this.carryWeight) {
            throw new ItemTooHeavyException();
        }
        // Set the destination floor
        this.destinationFloor = this.deliveryItem.getDestFloor();
    }
}
