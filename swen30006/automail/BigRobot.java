package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class BigRobot extends Robot{
	
	private final int TUBE_CAPACITY = 6;
	
	public BigRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong, boolean lower) {
		super(behaviour, mailPool, strong, lower);
		this.tube = new StorageTube(TUBE_CAPACITY);
	}
	
//	public int getCapacity() {
//		return TUBE_CAPACITY;
//	}
}
