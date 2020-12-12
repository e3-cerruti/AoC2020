package day12;

import static org.junit.jupiter.api.Assertions.*;

class FerryTest {

    @org.junit.jupiter.api.Test
    void turnLeft() {
        assertEquals(Direction.WEST, Direction.NORTH.turnLeft(90));
        assertEquals(Direction.NORTH, Direction.EAST.turnLeft(90));
        assertEquals(Direction.EAST, Direction.SOUTH.turnLeft(90));
        assertEquals(Direction.SOUTH, Direction.WEST.turnLeft(90));
    }

    @org.junit.jupiter.api.Test
    void turnRight() {
        assertEquals(Direction.EAST, Direction.NORTH.turnRight(90));
        assertEquals(Direction.SOUTH, Direction.EAST.turnRight(90));
        assertEquals(Direction.WEST, Direction.SOUTH.turnRight(90));
        assertEquals(Direction.NORTH, Direction.WEST.turnRight(90));
    }
}