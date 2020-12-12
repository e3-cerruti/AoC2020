package day12;


import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day12 {
    private static Logger logger = Logger.getLogger("day12.Day12");
    private final InputStream dataFile;
    private final Ferry ferry;
    private final WaypointOld waypointOld;

    public static void main(String[] args) {
        Day12 day = new Day12(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.processComands();
    }


    public Day12(String file) {
        dataFile = Day12.class.getResourceAsStream(file);
        ferry = new Ferry();
        waypointOld = new WaypointOld();
    }

    private void processComands() {
        try (Scanner input = new Scanner(dataFile)) {
            while (input.hasNextLine()) {
                String instruction = input.nextLine();
                String action = instruction.substring(0, 1);
                int value = Integer.parseInt(instruction.substring(1));

                logger.log(Level.FINE, "process({0}, {1})", new Object[]{action, value});
                ferry.processInstruction(action, value);
                waypointOld.processInstruction(action, value);
            }
        }

        System.out.format("Ferry: %d\n", ferry.getManhattanDistance());
        System.out.format("Waypoint: %d\n", waypointOld.getManhattanDistance());
    }
}
