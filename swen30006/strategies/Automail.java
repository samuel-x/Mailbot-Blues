package strategies;

import automail.Robot;

public class Automail {
	      
    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(String type1, String type2) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	//// Swap the next line for the one below
    	mailPool = new WeakStrongMailPool();
    	
        /** Initialize the RobotAction */
    	boolean weak = false;  // Can't handle more than 2000 grams
    	boolean strong = true; // Can handle any weight that arrives at the building
    	
    	//// Swap the next two lines for the two below those
    	//IRobotBehaviour robotBehaviourW = new MyRobotBehaviour(weak);
    	//IRobotBehaviour robotBehaviourS = new MyRobotBehaviour(strong);
    	    	
    	/** Initialize robot */
    	//robot1 = new Robot(robotBehaviourW, mailPool, weak); /* shared behaviour because identical and stateless */
    	//robot2 = new Robot(robotBehaviourS, mailPool, strong);
    	
    	robot1 = makeRobot(type1);
    	robot2 = makeRobot(type2);
    }
    
    private Robot makeRobot(String type) {
    	IRobotBehaviour tempBehaviour = null;
    	boolean strong = false;
    	if (type.equals("weak")) {
    		strong = false;
    		tempBehaviour = new MyRobotBehaviour(strong);
    	}
    	if (type.equals("strong")) {
    		strong = true;
    		tempBehaviour = new MyRobotBehaviour(strong);
    	}
    	if (type.equals("big")) {
    		System.out.println("gotta do big robot type");
    	}
    	
    	Robot robot = new Robot(tempBehaviour, mailPool, strong);
    	return robot;
    }
    
}
