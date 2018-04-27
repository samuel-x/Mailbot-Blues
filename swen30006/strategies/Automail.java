package strategies;

import automail.Robot;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail() {
    	// Swap between simple provided strategies and your strategies here

		// Initialize the MailPool

    	// Swap the next line for the one below
		this.mailPool = new WeakStrongMailPool();

		// Initialize the RobotAction
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	
    	// Swap the next two lines for the two below those
    	IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);

		// Initialize robot
		this.robot1 = new Robot(robotBehaviourW, this.mailPool, weak); // shared behaviour because identical and stateless
		this.robot2 = new Robot(robotBehaviourS, this.mailPool, strong);
    }
    
}
