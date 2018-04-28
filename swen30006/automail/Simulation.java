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

    public static void main(String[] args) {
        // Initialize the seed used for the random number generator.
        Integer seed = null;

        // Read the first argument and save it as a seed if it exists
        if (args.length != 0) {
            seed = Integer.parseInt(args[0]);
        }
      
        Automail automail = new Automail();

        // TODO: Replace the 300 (should be replaced with Properties.get____() that Sam is working on).
        MailGenerator generator = new MailGenerator(MAIL_TO_CREATE, 300, seed);

        // Initiate all the mail
        generator.generateAllMail();
        MailItem priorityMail;
        while (mailDelivered.size() != generator.MAIL_TO_CREATE) {
            generator.addMailToPool(automail.mailPool, Clock.Time());
            priorityMail = generator.getPriorityMailAtTime(Clock.Time());
            if (priorityMail != null) {
                automail.robot1.behaviour.priorityArrival(priorityMail.getPriorityLevel(), priorityMail.getWeight());
                automail.robot2.behaviour.priorityArrival(priorityMail.getPriorityLevel(), priorityMail.getWeight());
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
