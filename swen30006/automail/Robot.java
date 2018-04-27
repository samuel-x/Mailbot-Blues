package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.InvalidStateTransitionException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
import strategies.IRobotBehaviour;

/**
 * The robot delivers mail!
 */
public class Robot {

    StorageTube tube;
    IRobotBehaviour behaviour;
    public final String id;

    public RobotState currentState;

    private int currentFloor;
    private int destinationFloor;
    private IMailPool mailPool;
    private boolean strong;
    private MailItem deliveryItem;
    private int deliveryCounter;

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behaviour governs selection of mail items for delivery and behaviour on priority arrivals
     * @param mailPool is the source of mail items
     * @param strong is whether the robot can carry heavy items
     */
    public Robot(IRobotBehaviour behaviour, IMailPool mailPool, boolean strong) {
        this.id = "R" + hashCode();
        // currentState = RobotState.WAITING;
        this.currentState = RobotState.RETURNING;
        this.currentFloor = Building.MAILROOM_LOCATION;
        this.tube = new StorageTube();
        this.behaviour = behaviour;
        this.mailPool = mailPool;
        this.strong = strong;
        this.deliveryCounter = 0;
    }

    /**
     * This is called on every time step.
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling.
     * @throws ItemTooHeavyException if the robot is weak and tries to deliver a heavy object.
     * @throws InvalidStateTransitionException if the proposed state transition is invalid.
     */
    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException, InvalidStateTransitionException {
        switch(currentState) {
            // This state is triggered when the robot is returning to the mailroom after a delivery.
            case RETURNING:
                // If its current position is at the mailroom, then the robot should change state.
                if (this.currentFloor == Building.MAILROOM_LOCATION) {
                    while (!this.tube.isEmpty()) {
                        MailItem mailItem = this.tube.pop();
                        this.mailPool.addToPool(mailItem);
                        System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
                    }
                    changeState(RobotState.WAITING);
                } else {
                    // If the robot is not at the mailroom floor yet, then move towards it!
                    moveTowards(Building.MAILROOM_LOCATION);
                    break;
                }

            case WAITING:
                // Tell the sorter the robot is ready
                this.mailPool.fillStorageTube(this.tube, this.strong);
                // System.out.println("Tube total size: "+tube.getTotalOfSizes());
                // If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery.
                if (!this.tube.isEmpty()) {
                    this.deliveryCounter = 0; // reset delivery counter
                    this.behaviour.startDelivery();
                    setRoute();
                    changeState(RobotState.DELIVERING);
                }
                break;

            case DELIVERING:
                // Check whether or not the call to return is triggered manually.
                boolean wantToReturn = this.behaviour.returnToMailRoom(this.tube);
                if (this.currentFloor == this.destinationFloor) { // If already here drop off either way
                    // Delivery complete, report this to the simulator!
                    Simulation.reportDelivery(this.deliveryItem);
                    this.deliveryCounter++;
                    if (this.deliveryCounter > 4) {
                        throw new ExcessiveDeliveryException();
                    }
                    // Check if want to return or if there are more items in the tube
                    if (wantToReturn || this.tube.isEmpty()) {
                    // if(tube.isEmpty()){
                        changeState(RobotState.RETURNING);
                    } else {
                        /** If there are more items, set the robot's route to the location to deliver the item */
                        setRoute();
                        changeState(RobotState.DELIVERING);
                    }
                } else {/*
                    if(wantToReturn){
                        // Put the item we are trying to deliver back
                        try {
                            tube.addItem(deliveryItem);
                        } catch (TubeFullException e) {
                            e.printStackTrace();
                        }
                        changeState(RobotState.RETURNING);
                    }
                    else{*/
                    // The robot is not at the destination yet, move towards it!
                        moveTowards(this.destinationFloor);
                    /*
                    }
                    */
                }
                break;
        }
    }

    /**
     * Sets the route for the robot
     */
    private void setRoute() throws ItemTooHeavyException {
        // Pop the item from the StorageUnit
        this.deliveryItem = this.tube.pop();
        if (!this.strong && this.deliveryItem.getWeight() > 2000) {
            throw new ItemTooHeavyException();
        }
        // Set the destination floor
        this.destinationFloor = this.deliveryItem.getDestFloor();
    }

    /**
     * Generic function that moves the robot towards the destination
     * @param destination the floor towards which the robot is moving
     */
    private void moveTowards(int destination) {
        if (this.currentFloor < destination) {
            this.currentFloor++;
        } else {
            this.currentFloor--;
        }
    }
    
    /**
     * Prints out the change in state
     * @param newState the state to which the robot is transitioning
     * @throws InvalidStateTransitionException if the proposed state transition is invalid.
     */
    private void changeState(RobotState newState) throws InvalidStateTransitionException {
        checkValidStateTransition(this.currentState, newState);

        if (this.currentState != newState) {
            System.out.printf("T: %3d > %11s changed from %s to %s%n",
                    Clock.Time(), this.id, this.currentState, newState);
        }

        this.currentState = newState;

        if (newState == RobotState.DELIVERING) {
            System.out.printf("T: %3d > %11s-> [%s]%n", Clock.Time(), this.id, this.deliveryItem.toString());
        }
    }

    /**
     * Checks if the given before-state and after-state are valid.
     * @param currentState The current state.
     * @param newState The new state that is trying to be transitioned into.
     * @throws InvalidStateTransitionException if the proposed state transition is invalid.
     */
    private void checkValidStateTransition(RobotState currentState, RobotState newState)
            throws InvalidStateTransitionException {
        // This is essentially like not changing states at all, so just return.
        if (currentState == newState) {
            return;
        }

        switch (newState) {
            case DELIVERING:
                if (currentState != RobotState.WAITING) {
                    throw new InvalidStateTransitionException(currentState, newState);
                }
                break;

            case RETURNING:
                if (currentState != RobotState.DELIVERING) {
                    throw new InvalidStateTransitionException(currentState, newState);
                }
                break;

            case WAITING:
                if (currentState != RobotState.RETURNING) {
                    throw new InvalidStateTransitionException(currentState, newState);
                }
                break;
        }
    }
}
