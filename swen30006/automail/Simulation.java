package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.InvalidStateTransitionException;
import exceptions.InvalidRobotConfigException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;

import java.util.ArrayList;

/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {

    private static ArrayList<MailItem> mailDelivered = new ArrayList<>();
    private static double totalScore = 0;
    private static Properties properties;

    public static void main(String[] args) {

        // Read the .Properties file if it exists.
        if (args.length != 0) {
            properties = new Properties(args[0]);
        } else {
            throw new IllegalArgumentException("No arguments input. Please give a valid path.");
        }

        Building.init(properties.getMaxFloor());

        Automail automail = null;

        try {
            automail = new Automail(properties.getRobotType1(), properties.getRobotType2());
        }
        catch (InvalidRobotConfigException e) {
            e.printStackTrace();
            System.out.println("Simulation unable to complete.");
            System.exit(1);
        }

        MailGenerator generator = new MailGenerator(properties.getMailToCreate(),
                properties.getLastDeliveryTime(), properties.getSeed());
        
        // Initiate all the mail.
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
                System.exit(1);
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
        double priorityLevel = deliveryItem.getPriorityLevel();
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priorityLevel))
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(), properties.getDeliveryPenalty())
                * (1+Math.sqrt(priorityLevel));
    }
}
