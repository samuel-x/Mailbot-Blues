package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class BigRobot extends Robot{

    private final int TUBE_CAPACITY = 6;

    public BigRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong, BuildingSector sector) {
        super(behaviour, mailPool, strong, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }
}
