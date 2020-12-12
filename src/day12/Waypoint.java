package day12;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Waypoint extends Ferry {
    // position now refers to Waypoint position
    private final int[] ferryPosition = new int[]{0, 0};
    private static final Logger logger = Logger.getLogger("day12.Waypoint");

    private static final Map<String, BiConsumer<Waypoint, Integer>> dispatchTable =
            Map.of(
                    "N", Ferry::moveFerryNorth,
                    "E", Ferry::moveFerryEast,
                    "S", Ferry::moveFerrySouth,
                    "W", Ferry::moveFerryWest,
                    "L", Waypoint::rotateWaypointLeft,
                    "R", Waypoint::rotateWaypointRight,
                    "F", Waypoint::moveFerryForward
            );

    public Waypoint() {
        super();
        setPosition(new int[]{10, 1});
    }

    public void processInstruction(String action, int value) {
        dispatchTable.get(action).accept(this, value);
    }

    private void moveFerry(int x, int y) {
        ferryPosition[0] += x;
        ferryPosition[1] += y;
        logger.log(Level.FINE, "move({0}, {1}) -> ({2}, {3})", new Object[]{x, y, ferryPosition[0], ferryPosition[1]});
    }

    // Action L means to turn left the given number of degrees.
    public static void rotateWaypointLeft(Ferry ferry, int angle) {
        Waypoint waypoint = (Waypoint) ferry;
        int normalizedAngle = Direction.normalizeLeftAngle(angle);
        waypoint.setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(waypoint.getPosition()));
    }

    // Action R means to turn right the given number of degrees.
    public static void rotateWaypointRight(Ferry ferry, int angle) {
        Waypoint waypoint = (Waypoint) ferry;
        int normalizedAngle = Direction.normalizedRightAngle(angle);
        waypoint.setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(waypoint.getPosition()));
    }

    private void setWaypointPosition(int[] position) {
        setPosition(position);
    }

    // Action F means to move forward by the given value in the direction the ship is currently facing.
    public static void moveFerryForward(Waypoint waypoint, int distance) {
        waypoint.moveForward(distance);
    }

    private void moveForward(int distance) {
        int[] position = getPosition();
        moveFerry(position[0] * distance, position[1] * distance);
    }

    @Override
    public int getManhattanDistance() {
        return Math.abs(ferryPosition[0]) + Math.abs(ferryPosition[1]);
    }
}
