package strategies;

import automail.BigRobot;
import automail.Robot;
import automail.StrongRobot;
import automail.WeakRobot;
import exceptions.InvalidRobotConfigException;

public class Automail {

    public Robot robot1, robot2;
    public IMailPool mailPool;
    
    public Automail(String type1, String type2) throws InvalidRobotConfigException {
        // Swap between simple provided strategies and your strategies here

        /** Initialize the MailPool */

        //// Swap the next line for the one below
        mailPool = new WeakStrongMailPool();

        chooseOrdering(type1, type2);
    }

    private Robot makeRobot(String type, boolean lower) {
        IRobotBehaviour tempBehaviour = null;
        boolean strong = false;
        Robot robot = null;

        switch (type) {
            case "weak":
                strong = false;
                tempBehaviour = new MyRobotBehaviour(strong);
                robot = new WeakRobot(tempBehaviour, mailPool, strong, lower);
                break;
            case "strong":
                strong = true;
                tempBehaviour = new MyRobotBehaviour(strong);
                robot = new StrongRobot(tempBehaviour, mailPool, strong, lower);
                break;
            case "big":
                strong = true;
                tempBehaviour = new MyRobotBehaviour(strong);
                robot = new BigRobot(tempBehaviour, mailPool, strong, lower);
                break;
        }
        return robot;
    }

    private void chooseOrdering(String t1, String t2) throws InvalidRobotConfigException {

        if (t1.equals(t2) && !t1.equals("weak")) {
            robot1 = makeRobot(t1, true);
            robot2 = makeRobot(t2, false);
            return;
        } else if (t1.equals("weak") && !t2.equals("weak")) {
            robot1 = makeRobot(t1, false);
            robot2 = makeRobot(t2, true);
            return;
        } else if (t2.equals("weak") && !t1.equals("weak")) {
            robot1 = makeRobot(t2, false);
            robot2 = makeRobot(t1, true);
            return;
        } else if (t1.equals("strong")) {
            robot1 = makeRobot(t1, true);
            robot2 = makeRobot(t2, false);
            return;
        } else if (t2.equals("strong")) {
            robot1 = makeRobot(t2, true);
            robot2 = makeRobot(t1, false);
            return;
        } else {
            throw new InvalidRobotConfigException();
        }
    }
    
}
