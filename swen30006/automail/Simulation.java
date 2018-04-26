package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.InvalidStateTransitionException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.util.ArrayList;

/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {

    /** Constant for the mail generator */
    private static final int MAIL_TO_CREATE = 180;
  
    private static ArrayList<MailItem> mailDelivered = new ArrayList<>();
    private static double totalScore = 0;

    public static void main(String[] args) { //throws IOException {
 /*       // Should probably be using properties here
        Properties automailProperties = new Properties();
        // Defaults
        automailProperties.setProperty("Name_of_Property", "20");  // Property value may need to be converted from a string to the appropriate type

        FileReader inStream = null;

        try {
            inStream = new FileReader("automail.properties");
            automailProperties.load(inStream);
        } finally {
             if (inStream != null) {
                    inStream.close();
                }
        }

        int i = Integer.parseInt(automailProperties.getProperty("Name_of_Property"));
*/

        // Initialize the seed used for the random number generator.
        Integer seed = null;
        
        /** Read the first argument and save it as a seed if it exists */
        if(args.length != 0) {
            seed = Integer.parseInt(args[0]);
        }
      
        Automail automail = new Automail();

        // TODO: Replace the 300 (should be replaced with Properties.get____() that Sam is working on).
        MailGenerator generator = new MailGenerator(MAIL_TO_CREATE, 300, automail.mailPool, seed);
        
        /** Initiate all the mail */
        generator.generateAllMail();
        MailItem priority;
        while(mailDelivered.size() != generator.MAIL_TO_CREATE) {
            //System.out.println("-- Step: "+Clock.Time());
            priority = generator.step();
            if (priority != null) {
                automail.robot1.behaviour.priorityArrival(priority.getPriorityLevel(), priority.getWeight());
                automail.robot2.behaviour.priorityArrival(priority.getPriorityLevel(), priority.getWeight());
            }
            try {
                automail.robot1.step();
                automail.robot2.step();
            } catch (ExcessiveDeliveryException | ItemTooHeavyException | InvalidStateTransitionException e) {
                e.printStackTrace();
                System.out.println("Simulation unable to complete.");
                System.exit(0);
            }
            Clock.Tick();
        }
        printResults();
    }

    /**
     * Called when a Robot delivers an item in order to register that fact with the simulation.
     * @param deliveryItem The MailItem that was delivered.
     */
    public static void reportDelivery(MailItem deliveryItem) {
        if (!mailDelivered.contains(deliveryItem)) {
            System.out.printf("T: %3d > Delivered     [%s]%n", Clock.Time(), deliveryItem.toString());
            mailDelivered.add(deliveryItem);
            // Calculate delivery score
            totalScore += calculateDeliveryScore(deliveryItem);

        } else {
            try {
                throw new MailAlreadyDeliveredException();
            } catch (MailAlreadyDeliveredException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printResults() {
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: " + Clock.Time());
        System.out.printf("Final Score: %.2f%n", totalScore);
    }

    private static double calculateDeliveryScore(MailItem deliveryItem) {
        // Penalty for longer delivery times
        final double penalty = 1.1;
        double priority_weight = deliveryItem.getPriorityLevel();
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),penalty)*(1+Math.sqrt(priority_weight));
    }
}
