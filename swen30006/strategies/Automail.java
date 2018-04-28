package strategies;

import automail.*;
import exceptions.InvalidRobotConfigException;

public class Automail {

    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(String type1, String type2) throws InvalidRobotConfigException {
        // Swap between simple provided strategies and your strategies here

        /** Initialize the MailPool */

        //// Swap the next line for the one below
        mailPool = new WeakStrongMailPool();

        createRobots(type1, type2);
    }

    private Robot makeRobot(String type, BuildingSector sector) {
        IRobotBehaviour tempBehaviour;
        Robot robot = null;

        switch (type) {
            case "weak":
                tempBehaviour = new MyRobotBehaviour(false, sector);
                robot = new WeakRobot(tempBehaviour, mailPool, false, sector);
                break;
            case "strong":
                tempBehaviour = new MyRobotBehaviour(true, sector);
                robot = new StrongRobot(tempBehaviour, mailPool, true, sector);
                break;
            case "big":
                tempBehaviour = new MyRobotBehaviour(true, sector);
                robot = new BigRobot(tempBehaviour, mailPool, true, sector);
                break;
        }
        return robot;
    }

    private void createRobots(String t1, String t2) throws InvalidRobotConfigException {
        if (t1.equals(t2) && !t1.equals("weak")) {
            robot1 = makeRobot(t1, BuildingSector.LOWER);
            robot2 = makeRobot(t2, BuildingSector.UPPER);
            return;
        } else if (t1.equals("weak") && !t2.equals("weak")) {
            robot1 = makeRobot(t1, BuildingSector.UPPER);
            robot2 = makeRobot(t2, BuildingSector.LOWER);
            return;
        } else if (t2.equals("weak") && !t1.equals("weak")) {
            robot1 = makeRobot(t2, BuildingSector.UPPER);
            robot2 = makeRobot(t1, BuildingSector.LOWER);
            return;
        } else if (t1.equals("strong")) {
            robot1 = makeRobot(t1, BuildingSector.LOWER);
            robot2 = makeRobot(t2, BuildingSector.UPPER);
            return;
        } else if (t2.equals("strong")) {
            robot1 = makeRobot(t2, BuildingSector.LOWER);
            robot2 = makeRobot(t1, BuildingSector.UPPER);
            return;
        } else {
            throw new InvalidRobotConfigException();
        }
    }
}
