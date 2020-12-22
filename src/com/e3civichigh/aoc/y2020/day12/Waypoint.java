package com.e3civichigh.aoc.y2020.day12;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Waypoint extends Ferry {
    // position now refers to Waypoint position
    private final int[] ferryPosition = new int[]{0, 0};
    private static final Logger logger = Logger.getLogger("com.e3civichigh.aoc.y2020.day12.Waypoint");


    public Waypoint() {
        super();
        setPosition(new int[]{10, 1});
        Map<String, Consumer<Integer>> aTable = new HashMap<>(dispatchTable);
        aTable.put("L", this::rotateLeft);
        aTable.put("R", this::rotateRight);
        dispatchTable = aTable;
    }

    private void moveFerry(int x, int y) {
        ferryPosition[0] += x;
        ferryPosition[1] += y;
        logger.log(Level.FINE, "move({0}, {1}) -> ({2}, {3})", new Object[]{x, y, ferryPosition[0], ferryPosition[1]});
    }

    // Action L means to turn left the given number of degrees.
    public void rotateLeft(int angle) {
        int normalizedAngle = Direction.normalizeLeftAngle(angle);
        setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(getPosition()));
    }

    // Action R means to turn right the given number of degrees.
    public void rotateRight(int angle) {
        int normalizedAngle = Direction.normalizedRightAngle(angle);
        setWaypointPosition(Direction.fromDegrees(normalizedAngle)
                .rotateWaypoint(getPosition()));
    }

    private void setWaypointPosition(int[] position) {
        setPosition(position);
    }

    // Action F means to move forward by the given value in the direction the ship is currently facing.
    @Override
    protected void moveForward(int distance) {
        int[] position = getPosition();
        moveFerry(position[0] * distance, position[1] * distance);
    }

    @Override
    public int getManhattanDistance() {
        return Math.abs(ferryPosition[0]) + Math.abs(ferryPosition[1]);
    }
}
