package day12;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ferry {
    private static final Logger logger = Logger.getLogger("day12.Ferry");

    private final int[] position = new int[2];
    private Direction direction = Direction.EAST;
    private static final Map<String, BiConsumer<Ferry, Integer>> dispatchTable =
            Map.of(
                    "N", Ferry::moveFerryNorth,
                    "E", Ferry::moveFerryEast,
                    "S", Ferry::moveFerrySouth,
                    "W", Ferry::moveFerryWest,
                    "L", Ferry::turnFerryLeft,
                    "R", Ferry::turnFerryRight,
                    "F", Ferry::moveFerryForward
            );

    public void processInstruction(String action, int value) {
        dispatchTable.get(action).accept(this, value);
    }

    private void move(int x, int y) {
        position[0] += x;
        position[1] += y;
        logger.log(Level.FINE, "move({0}, {1}) -> ({2}, {3})", new Object[]{x, y, position[0], position[1]});
    }

    // Actions
    // Action N means to move north by the given value.
    public static void moveFerryNorth(Ferry ferry, int distance) {
        ferry.moveNorth(distance);
    }

    private void moveNorth(int distance) {
        move(0, distance);
    }

    // Action S means to move south by the given value.
    public static void moveFerrySouth(Ferry ferry, int distance) {
        ferry.moveSouth(distance);
    }

    private void moveSouth(int distance) {
        move(0, -distance);
    }

    // Action E means to move east by the given value.
    public static void moveFerryEast(Ferry ferry, int distance) {
        ferry.moveEast(distance);
    }

    private void moveEast(int distance) {
        move(distance, 0);
    }

    // Action W means to move west by the given value.
    public static void moveFerryWest(Ferry ferry, int distance) {
        ferry.moveWest(distance);
    }

    private void moveWest(int distance) {
        move(-distance, 0);
    }

    // Action L means to turn left the given number of degrees.
    public static void turnFerryLeft(Ferry ferry, int angle) {
        ferry.turnLeft(angle);
    }

    private void turnLeft(int angle) {
        direction = direction.turnLeft(angle);
    }

    // Action R means to turn right the given number of degrees.
    public static void turnFerryRight(Ferry ferry, int angle) {
        ferry.turnRight(angle);
    }

    private void turnRight(int angle) {
        direction = direction.turnRight(angle);
    }

    // Action F means to move forward by the given value in the direction the ship is currently facing.
    public static void moveFerryForward(Ferry ferry, int distance) {
        ferry.direction.move(ferry, distance);
    }

    @SuppressWarnings("unused")
    private void moveForward(int distance) {
        direction.move(this, distance);
    }

    protected void setPosition(int[] position) {
        this.position[0] = position[0];
        this.position[1] = position[1];
    }

    public int[] getPosition() {
        return position;
    }

    public int getManhattanDistance() {
        return Math.abs(position[0]) + Math.abs(position[1]);
    }

}
