package automail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class represents the input properties of the simulation
 */
public class Properties {
        private int seed;
        private int maxFloor;
        private float deliveryPenalty;
        private int lastDeliveryTime;
        private int mailToCreate;
        private String robotType1;
        private String robotType2;

        public Properties(String path) {
            // Use a stream to capture all configuration lines and parse them into values
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                stream.filter(line -> !line.startsWith("#"))
                        .forEach(this::load);
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
                if (parseValue(line).equals(" ")) {
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
         * @param line String containing the parameter to be configured
         * @return
         */
        private String parseValue(String line) {
                return line.substring(line.lastIndexOf("=") + 1);
            }

        public int getSeed() {
            return this.seed;
        }

        public int getMaxFloor() {
            return this.maxFloor;
        }

        public float getDeliveryPenalty() {
            return this.deliveryPenalty;
        }

        public int getLastDeliveryTime() {
            return this.lastDeliveryTime;
        }

        public int getMailToCreate() {
            return this.mailToCreate;
        }

        public String getRobotType1() {
            return this.robotType1;
        }

        public String getRobotType2() {
            return this.robotType2;
        }
}
