package day12;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Waypoint {
    private final int[] position = new int[]{10, 1};
    private final int[] ferryPosition = new int[]{0, 0};

    private static final Map<String, BiConsumer<Waypoint, Integer>> dispatchTable =
            Map.of(
                    "N", Waypoint::moveWaypointNorth,
                    "E", Waypoint::moveWaypointEast,
                    "S", Waypoint::moveWaypointSouth,
                    "W", Waypoint::moveWaypointWest,
                    "L", Waypoint::rotateWaypointLeft,
                    "R", Waypoint::rotateWaypointRight,
                    "F", Waypoint::moveFerryForward
            );
    private static final Logger logger = Logger.getLogger("day12.Ferry");

    public enum Direction {
        NORTH (0, new int[][]{new int[]{1,0}, new int[]{0,1}}),
        EAST (90, new int[][]{new int[]{0,1}, new int[]{-1, 0}}),
        SOUTH (180, new int[][]{new int[]{-1,0}, new int[]{0,-1}}),
        WEST (270, new int[][]{new int[]{0,-1}, new int[]{1, 0}});

        private final int degrees;
        private final int[][] rotationMatrix;

        Direction(int degrees, int[][] rotationMatrix) {
            this.degrees = degrees;
            this.rotationMatrix = rotationMatrix;
        }

        public int[] rotateWaypoint(int[] position) {
            int x = rotationMatrix[0][0] * position[0] + rotationMatrix[0][1] * position[1];
            int y = rotationMatrix[1][0] * position[0] + rotationMatrix[1][1] * position[1];
            return new int[]{x, y};
        }

        public static int normalizeLeftAngle(int angle) {
            return (360 - (angle % 360) ) % 360;
        }

        public static int normalizedRightAngle(int angle) {
            return angle % 360;
        }

        private static Direction fromDegrees(int angle) {
            for (Direction v : values()) {
                if (v.degrees == angle) {
                    return v;
                }
            }
            return null; // TODO: Should this throw an exception?
        }
    }

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
    public static void moveWaypointNorth(Waypoint waypoint, int distance) {
        waypoint.moveNorth(distance);
    }

    private void moveNorth(int distance) {
        move(0, distance);
    }

    // Action S means to move south by the given value.
    public static void moveWaypointSouth(Waypoint waypoint, int distance) {
        waypoint.moveSouth(distance);
    }

    private void moveSouth(int distance) {
        move(0, -distance);
    }

    // Action E means to move east by the given value.
    public static void moveWaypointEast(Waypoint waypoint, int distance) {
        waypoint.moveEast(distance);
    }

    private void moveEast(int distance) {
        move(distance, 0);
    }

    // Action W means to move west by the given value.
    public static void moveWaypointWest(Waypoint waypoint, int distance) {
        waypoint.moveWest(distance);
    }

    private void moveWest(int distance) {
        move(-distance, 0);
    }

    // Action L means to turn left the given number of degrees.
    public static void rotateWaypointLeft(Waypoint waypoint, int angle) {
        int normalizedAngle = Direction.normalizeLeftAngle(angle);
        waypoint.setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(waypoint.position));
    }

    // Action R means to turn right the given number of degrees.
    public static void rotateWaypointRight(Waypoint waypoint, int angle) {
        int normalizedAngle = Direction.normalizedRightAngle(angle);
        waypoint.setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(waypoint.position));
    }

    private void setWaypointPosition(int[] position) {
        this.position[0] = position[0];
        this.position[1] = position[1];
    }

    // Action F means to move forward by the given value in the direction the ship is currently facing.
    public static void moveFerryForward(Waypoint waypoint, int distance) {
        waypoint.moveForward(distance);
    }

    private void moveForward(int distance) {
        moveFerry(position[0] * distance, position[1] * distance);
    }

    public int getManhattanDistance() {
        return Math.abs(ferryPosition[0]) + Math.abs(ferryPosition[1]);
    }


}
