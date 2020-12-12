package day12;

import static org.junit.jupiter.api.Assertions.*;

class FerryTest {

    @org.junit.jupiter.api.Test
    void turnLeft() {
        assertEquals(Ferry.Direction.WEST, Ferry.Direction.NORTH.turnLeft(90));
        assertEquals(Ferry.Direction.NORTH, Ferry.Direction.EAST.turnLeft(90));
        assertEquals(Ferry.Direction.EAST, Ferry.Direction.SOUTH.turnLeft(90));
        assertEquals(Ferry.Direction.SOUTH, Ferry.Direction.WEST.turnLeft(90));
    }

    @org.junit.jupiter.api.Test
    void turnRight() {
        assertEquals(Ferry.Direction.EAST, Ferry.Direction.NORTH.turnRight(90));
        assertEquals(Ferry.Direction.SOUTH, Ferry.Direction.EAST.turnRight(90));
        assertEquals(Ferry.Direction.WEST, Ferry.Direction.SOUTH.turnRight(90));
        assertEquals(Ferry.Direction.NORTH, Ferry.Direction.WEST.turnRight(90));
    }
}