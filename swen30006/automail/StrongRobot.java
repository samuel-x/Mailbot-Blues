package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class StrongRobot extends Robot{

    private final int TUBE_CAPACITY = 4;

    public StrongRobot(IRobotBehaviour behaviour, IMailPool mailPool, int carryWeight, BuildingSector sector) {
        super(behaviour, mailPool, carryWeight, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }

    @Override
    protected void setRoute() {
        // Pop the item from the StorageUnit
        this.deliveryItem = this.storage.pop();
        // Set the destination floor
        this.destinationFloor = this.deliveryItem.getDestFloor();
    }

}
