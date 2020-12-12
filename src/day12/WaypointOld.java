package day12;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaypointOld {
    private final int[] position = new int[]{10, 1};
    private final int[] ferryPosition = new int[]{0, 0};

    private static final Map<String, BiConsumer<WaypointOld, Integer>> dispatchTable =
            Map.of(
                    "N", WaypointOld::moveWaypointNorth,
                    "E", WaypointOld::moveWaypointEast,
                    "S", WaypointOld::moveWaypointSouth,
                    "W", WaypointOld::moveWaypointWest,
                    "L", WaypointOld::rotateWaypointLeft,
                    "R", WaypointOld::rotateWaypointRight,
                    "F", WaypointOld::moveFerryForward
            );
    private static final Logger logger = Logger.getLogger("day12.Ferry");

    public void processInstruction(String operation, int operand) {
        dispatchTable.get(operation).accept(this, operand);
    }

    private void move(int x, int y) {
        position[0] += x;
        position[1] += y;
        logger.log(Level.FINE, "move({0}, {1}) -> ({2}, {3})", new Object[]{x, y, position[0], position[1]});
    }

    private void moveFerry(int x, int y) {
        ferryPosition[0] += x;
        ferryPosition[1] += y;
        logger.log(Level.FINE, "move({0}, {1}) -> ({2}, {3})", new Object[]{x, y, ferryPosition[0], ferryPosition[1]});
    }

    // Actions
    // Action N means to move north by the given value.
    public static void moveWaypointNorth(WaypointOld waypointOld, int distance) {
        waypointOld.moveNorth(distance);
    }

    private void moveNorth(int distance) {
        move(0, distance);
    }

    // Action S means to move south by the given value.
    public static void moveWaypointSouth(WaypointOld waypointOld, int distance) {
        waypointOld.moveSouth(distance);
    }

    private void moveSouth(int distance) {
        move(0, -distance);
    }

    // Action E means to move east by the given value.
    public static void moveWaypointEast(WaypointOld waypointOld, int distance) {
        waypointOld.moveEast(distance);
    }

    private void moveEast(int distance) {
        move(distance, 0);
    }

    // Action W means to move west by the given value.
    public static void moveWaypointWest(WaypointOld waypointOld, int distance) {
        waypointOld.moveWest(distance);
    }

    private void moveWest(int distance) {
        move(-distance, 0);
    }

    // Action L means to turn left the given number of degrees.
    public static void rotateWaypointLeft(WaypointOld waypointOld, int angle) {
        int normalizedAngle = Direction.normalizeLeftAngle(angle);
        waypointOld.setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(waypointOld.position));
    }

    // Action R means to turn right the given number of degrees.
    public static void rotateWaypointRight(WaypointOld waypointOld, int angle) {
        int normalizedAngle = Direction.normalizedRightAngle(angle);
        waypointOld.setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(waypointOld.position));
    }

    private void setWaypointPosition(int[] position) {
        this.position[0] = position[0];
        this.position[1] = position[1];
    }

    // Action F means to move forward by the given value in the direction the ship is currently facing.
    public static void moveFerryForward(WaypointOld waypointOld, int distance) {
        waypointOld.moveForward(distance);
    }

    private void moveForward(int distance) {
        moveFerry(position[0] * distance, position[1] * distance);
    }

    public int getManhattanDistance() {
        return Math.abs(ferryPosition[0]) + Math.abs(ferryPosition[1]);
    }


}
