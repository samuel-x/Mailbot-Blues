package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class StrongRobot extends Robot{

    private final int TUBE_CAPACITY = 4;

    public StrongRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong, BuildingSector sector) {
        super(behaviour, mailPool, strong, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }
}
