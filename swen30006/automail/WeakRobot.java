package automail;

import strategies.IMailPool;
import strategies.IRobotBehaviour;

public class WeakRobot extends Robot{
	
	public static final int TUBE_CAPACITY = 4;
	private final int CARRY_WEIGHT = 2000;
	
	public WeakRobot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong) {
		super(behaviour, mailPool, strong);
		this.tube = new StorageTube(TUBE_CAPACITY);
	}
	
	public int getCarryWeight() {
		return CARRY_WEIGHT;
	}
	
//	public int getCapacity() {
//		return TUBE_CAPACITY;
//	}
}
