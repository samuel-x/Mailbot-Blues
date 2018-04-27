package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class BigRobot extends Robot{
	
	public static final int TUBE_CAPACITY = 6;
	
	public BigRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong) {
		super(behaviour, mailPool, strong);
		this.tube = new StorageTube(TUBE_CAPACITY);
	}
	
	public int getCapacity() {
		return TUBE_CAPACITY;
	}
}
