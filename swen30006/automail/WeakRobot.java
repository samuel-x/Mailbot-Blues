package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class WeakRobot extends Robot{
	
	private final int TUBE_CAPACITY = 4;
	private final int CARRY_WEIGHT = 2000;
	
	public WeakRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong, boolean lower) {
		super(behaviour, mailPool, strong, lower);
		this.tube = new StorageTube(TUBE_CAPACITY);
	}
	
	public int getCarryWeight() {
		return CARRY_WEIGHT;
	}
	
//	public int getCapacity() {
//		return TUBE_CAPACITY;
//	}
}
