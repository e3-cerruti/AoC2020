package day12;

import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ferry {
    private static final Logger logger = Logger.getLogger("day12.Ferry");

    private final int[] position = new int[2];
    private Direction direction = Direction.EAST;
    protected Map<String, Consumer<Integer>> dispatchTable =
            Map.of(
                    "N", this::moveNorth,
                    "E", this::moveEast,
                    "S", this::moveSouth,
                    "W", this::moveWest,
                    "L", this::turnLeft,
                    "R", this::turnRight,
                    "F", this::moveForward
            );

    public void processInstruction(String action, int value) {
        dispatchTable.get(action).accept(value);
    }

    private void move(int x, int y) {
        position[0] += x;
        position[1] += y;
        logger.log(Level.FINE, "move({0}, {1}) -> ({2}, {3})", new Object[]{x, y, position[0], position[1]});
    }

    // Actions
    // Action N means to move north by the given value.
    protected void moveNorth(int distance) {
        move(0, distance);
    }

    // Action S means to move south by the given value.
    protected void moveSouth(int distance) {
        move(0, -distance);
    }

    // Action E means to move east by the given value.
    protected void moveEast(int distance) {
        move(distance, 0);
    }

    // Action W means to move west by the given value.
    protected void moveWest(int distance) {
        move(-distance, 0);
    }

    // Action L means to turn left the given number of degrees.
    private void turnLeft(int angle) {
        direction = direction.turnLeft(angle);
    }

    // Action R means to turn right the given number of degrees.
    private void turnRight(int angle) {
        direction = direction.turnRight(angle);
    }

    // Action F means to move forward by the given value in the direction the ship is currently facing.
    protected void moveForward(int distance) {
        direction.move(this, distance);
    }

    protected void setPosition(int[] position) {
        this.position[0] = position[0];
        this.position[1] = position[1];
    }

    protected int[] getPosition() {
        return position;
    }

    public int getManhattanDistance() {
        return Math.abs(position[0]) + Math.abs(position[1]);
    }

}
