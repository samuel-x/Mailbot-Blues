package strategies;

import automail.*;
import exceptions.InvalidRobotConfigException;

/**
 * This represents the automail class, the class which represents the automailing system.
 * It is responsible for giving different robots different behaviours depending on the strategy implemeneted.
 */
public class Automail {

    // Our robots and out mailpool
    public Robot robot1, robot2;
    public IMailPool mailPool;

    /**
     * This initialises our robots and mailpool
     * @param type1 Type of the first robot
     * @param type2 Type of the second robot
     * @throws InvalidRobotConfigException
     */
    public Automail(String type1, String type2) throws InvalidRobotConfigException {
        // Swap between simple provided strategies and your strategies here

        /** Initialize the MailPool */

        //// Swap the next line for the one below
        mailPool = new WeakStrongMailPool(Simulation.CARRY_WEIGHT);

        createRobots(type1, type2);
    }

    /**
     * This method creates and returns a robot with a given type and sector
     * @param type The type of robot to create
     * @param sector The sector of the building the robot will operate in
     * @return A robot
     */
    private Robot makeRobot(String type, BuildingSector sector) {
        IRobotBehaviour tempBehaviour;
        Robot robot = null;

        // Set our robot's carry weight if it is weak
        int carryWeight = type.equals("weak") ? Simulation.CARRY_WEIGHT : Integer.MAX_VALUE;

        switch (type) {
            case "weak":
                tempBehaviour = new MyRobotBehaviour(sector);
                robot = new WeakRobot(tempBehaviour, mailPool, carryWeight, sector);
                break;
            case "strong":
                tempBehaviour = new MyRobotBehaviour(sector);
                robot = new StrongRobot(tempBehaviour, mailPool, carryWeight, sector);
                break;
            case "big":
                tempBehaviour = new MyRobotBehaviour(sector);
                robot = new BigRobot(tempBehaviour, mailPool, carryWeight, sector);
                break;
        }
        return robot;
    }

    /**
     * This method creates a specific robot given a type
     * @param t1 Type of the first robot
     * @param t2 Type of the second robot
     * @throws InvalidRobotConfigException
     */
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
