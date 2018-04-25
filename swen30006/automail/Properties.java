package automail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class represents the input properties of the simulation
 */
public class Properties {
        private static int seed;
        private static int maxFloor;
        private static float deliveryPenalty;
        private static int lastDeliveryTime;
        private static int mailToCreate;
        private static String robotType1;
        private static String robotType2;

        public Properties(String path) {
            // Use a stream to capture all configuration lines and parse them into values
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                stream.filter(line -> !line.startsWith("#"))
                                .forEach(line -> load(line));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * This method loads our values in depending on the specified value in the line.
         * @param line
         */
        private void load(String line) {
            if (line.contains("Seed")) {
                // If we have no seed, return 0
                if (parseValue(line) == " ") {
                    seed = 0;
                }
                else {
                    seed = Integer.parseInt(parseValue(line));
                }
            }
            else if (line.contains("Number_of_Floors")) {
                maxFloor = Integer.parseInt(parseValue(line));
            }
            else if (line.contains("Delivery_Penalty")) {
                deliveryPenalty = Float.parseFloat(parseValue(line));
            }
            else if (line.contains("Last_Delivery_Time")) {
                lastDeliveryTime = Integer.parseInt(parseValue(line));
            }
            else if (line.contains("Mail_to_Create")) {
                mailToCreate = Integer.parseInt(parseValue(line));
            }
            else if (line.contains("Robot_Type_1")) {
                robotType1 = parseValue(line);
            }
            else if (line.contains("Robot_Type_2")) {
                robotType2 = parseValue(line);
            }
        }

        /**
         * This simply returns a string of the value of a variable in the .properties file.
         * @param line
         * @return
         */
        private String parseValue(String line) {
                return line.substring(line.lastIndexOf("=") + 1);
            }

        public int getSeed() {
            return seed;
        }

        public int getMaxFloor() {
            return maxFloor;
        }

        public float getDeliveryPenalty() {
            return deliveryPenalty;
        }

        public int getLastDeliveryTime() {
            return lastDeliveryTime;
        }

        public int getMailToCreate() {
            return mailToCreate;
        }

        public String getRobotType1() {
            return robotType1;
        }

        public String getRobotType2() {
            return robotType2;
        }
}
