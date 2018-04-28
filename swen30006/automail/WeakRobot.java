package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class WeakRobot extends Robot{
    public static final int CARRY_WEIGHT = 2000;

    private final int TUBE_CAPACITY = 4;

    public WeakRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong, BuildingSector sector) {
        super(behaviour, mailPool, strong, sector);
        this.storage = new StorageTube(TUBE_CAPACITY);
    }
}
