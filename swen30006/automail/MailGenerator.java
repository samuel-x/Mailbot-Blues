package automail;

import strategies.IMailPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This class generates the mail.
 * Constants in this class are based on observations of typical mail arrivals
 */
public class MailGenerator {

    public final int MAIL_TO_CREATE;
    public final int LAST_DELIVERY_TIME;

    private final Random random;

    private int mailCreated;
    private boolean complete;
    private HashMap<Integer, ArrayList<MailItem>> allMail = new HashMap<>();

    /**
     * Constructor for mail generation
     * @param mailToCreate roughly how many mail items to create
     * @param seed random seed for generating mail
     */
    public MailGenerator(int mailToCreate, int lastDeliveryTime, Integer seed) {
        if (seed != null) {
            this.random = new Random(seed);
        } else {
            this.random = new Random();
        }
        // Vary arriving mail by +/-20%
        this.MAIL_TO_CREATE = mailToCreate*4/5 + random.nextInt(mailToCreate * 2/5);
        this.LAST_DELIVERY_TIME = lastDeliveryTime;

        this.mailCreated = 0;
        this.complete = false;
    }

    /**
     * @return a new mail item that needs to be delivered
     */
    private MailItem generateMail() {
        int destinationFloor = generateDestinationFloor();
        // Cannot move this line down into the below 'else' block, which would result in only generating priority levels
        // when a priority mail item has been generated. This is because it would result in fewer generations of
        // priority levels, affecting the random number generator, changing final output of the program, which is not
        // allowed.
        int priorityLevel = generatePriorityLevel();
        int arrivalTime = generateArrivalTime();
        int weight = generateWeight();

        // Check if arrival time has a priority mail
        if ((this.random.nextInt(6) > 0) ||  // Skew towards non priority mail
                (this.allMail.containsKey(arrivalTime) &&
                        this.allMail.get(arrivalTime).stream().anyMatch(MailItem::hasPriority)))
        {
            return new MailItem(destinationFloor, arrivalTime, weight, 0);
        } else {
            return new MailItem(destinationFloor, arrivalTime, weight, priorityLevel);
        }   
    }

    /**
     * @return a destination floor between the ranges of GROUND_FLOOR to FLOOR
     */
    private int generateDestinationFloor() {
        return Building.LOWEST_FLOOR + this.random.nextInt(Building.FLOORS);
    }

    /**
     * @return a random priority level selected from 10 and 100
     */
    private int generatePriorityLevel() {
        return this.random.nextInt(4) > 0 ? 10 : 100;
    }
    
    /**
     * @return a random weight
     */
    private int generateWeight(){
        final double mean = 200.0; // grams for normal item
        final double stddev = 700.0; // grams
        double base = this.random.nextGaussian();
        if (base < 0) base = -base;
        int weight = (int) (mean + base * stddev);
        return weight > 5000 ? 5000 : weight;
    }
    
    /**
     * @return a random arrival time before the last delivery time
     */
    private int generateArrivalTime() {
        return 1 + this.random.nextInt(this.LAST_DELIVERY_TIME);
    }

    /**
     * This class initializes all mail and sets their corresponding values,
     */
    public void generateAllMail() {
        while (!this.complete) {
            MailItem newMail =  generateMail();
            int timeToDeliver = newMail.getArrivalTime();
            // Check if key exists for this time
            if (this.allMail.containsKey(timeToDeliver)) {
                // Add to existing array
                this.allMail.get(timeToDeliver).add(newMail);
            } else {
                // If the key doesn't exist then set a new key along with the array of MailItems to add during
                // that time step.
                ArrayList<MailItem> newMailList = new ArrayList<>();
                newMailList.add(newMail);
                this.allMail.put(timeToDeliver, newMailList);
            }
            // Mark the mail as created
            this.mailCreated++;

            // Once we have satisfied the amount of mail to create, we're done!
            if (this.mailCreated == this.MAIL_TO_CREATE) {
                this.complete = true;
            }
        }
    }

    /**
     * Adds all mail from the given time to the given mail pool.
     * @param mailPool The mail pool to add mail to.
     * @param timeStamp The time stamp from which mail is added.
     */
    public void addMailToPool(IMailPool mailPool, int timeStamp) {
        // Check if there are any mail to add.
        if (this.allMail.containsKey(timeStamp)) {
            for (MailItem mailItem : this.allMail.get(timeStamp)) {
                System.out.printf("T: %3d > new addToPool [%s]%n", Clock.Time(), mailItem.toString());
                mailPool.addToPool(mailItem);
            }
        }
    }

    /**
     * Get the priority mail item at the given time, if present.
     * @param timeStamp The time stamp that's being checking for priority mail.
     * @return The mail item if present. Otherwise, returns null.
     */
    public MailItem getPriorityMailAtTime(int timeStamp) {
        ArrayList<MailItem> items = this.allMail.get(timeStamp);

        return items == null ? null : items.stream().filter(MailItem::hasPriority).findFirst().orElse(null);
    }
}
