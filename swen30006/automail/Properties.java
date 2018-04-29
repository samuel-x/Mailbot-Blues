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
        private double deliveryPenalty;
        private int lastDeliveryTime;
        private int mailToCreate;
        private String robotType1;
        private String robotType2;

        public Properties(String path) {
            // Use a stream to capture all configuration lines and parse them into values
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                stream.filter(line -> !line.startsWith("#"))
                        .forEach(this::load);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * This method loads our values in depending on the specified value in the line.
         * @param line A given line with a value in the class.
         */
        private void load(String line) {
            String argument = line.substring(0, line.indexOf("="));

            switch (argument) {
                case "Seed":
                    // If we have no seed, return 0
                    if (parseValue(line).equals(" ")) {
                        this.seed = 0;
                    } else {
                        this.seed = Integer.parseInt(parseValue(line));
                    }
                    break;

                case "Number_of_Floors":
                    this.maxFloor = Integer.parseInt(parseValue(line));
                    break;

                case "Delivery_Penalty":
                    this.deliveryPenalty = Double.parseDouble(parseValue(line));
                    break;

                case "Last_Delivery_Time":
                    this.lastDeliveryTime = Integer.parseInt(parseValue(line));
                    break;

                case "Mail_to_Create":
                    this.mailToCreate = Integer.parseInt(parseValue(line));
                    break;

                case "Robot_Type_1":
                    this.robotType1 = parseValue(line);
                    break;

                case "Robot_Type_2":
                    this.robotType2 = parseValue(line);
                    break;
            }
        }

        /**
         * This simply returns a string of the value of a variable in the .properties file.
         * @param line String containing the parameter to be configured
         * @return The value defined in the line.
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

        public double getDeliveryPenalty() {
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
