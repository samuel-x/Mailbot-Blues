package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class StrongRobot extends Robot{
	
	private final int TUBE_CAPACITY = 4;
	
	public StrongRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong) {
		super(behaviour, mailPool, strong);
		this.tube = new StorageTube(TUBE_CAPACITY);
	}
	
//	public int getCapacity() {
//		return TUBE_CAPACITY;
//	}
}
