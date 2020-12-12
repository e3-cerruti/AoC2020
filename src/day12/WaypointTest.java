package day12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaypointTest {

    private WaypointOld waypointOld;

    @BeforeEach
    void setUp() {
        waypointOld = new WaypointOld();
    }

    @Test
    void moveNorth() {
        WaypointOld.moveWaypointNorth(waypointOld, 10);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(21, waypointOld.getManhattanDistance());
    }

    @Test
    void moveEast() {
        WaypointOld.moveWaypointEast(waypointOld, 10);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(21, waypointOld.getManhattanDistance());
    }

    @Test
    void moveSouth() {
        WaypointOld.moveWaypointSouth(waypointOld, 10);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(19, waypointOld.getManhattanDistance());
    }

    @Test
    void moveWest() {
        WaypointOld.moveWaypointWest(waypointOld, 10);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(1, waypointOld.getManhattanDistance());
    }

    @Test
    void rotateLeft() {
        WaypointOld.moveFerryForward(waypointOld, 1);
        WaypointOld.rotateWaypointLeft(waypointOld, 90);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(20, waypointOld.getManhattanDistance());
    }

    @Test
    void rotateLeft2() {
        WaypointOld.moveFerryForward(waypointOld, 1);
        WaypointOld.rotateWaypointLeft(waypointOld, 180);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(0, waypointOld.getManhattanDistance());
    }

    @Test
    void rotateLeft3() {
        WaypointOld.moveFerryForward(waypointOld, 1);
        WaypointOld.rotateWaypointLeft(waypointOld, 270);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(20, waypointOld.getManhattanDistance());
    }

    @Test
    void rotateRigth() {
        WaypointOld.moveFerryForward(waypointOld, 1);
        WaypointOld.rotateWaypointLeft(waypointOld, 90);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(20, waypointOld.getManhattanDistance());
    }

    @Test
    void rotateRight2() {
        WaypointOld.moveFerryForward(waypointOld, 1);
        WaypointOld.rotateWaypointLeft(waypointOld, 180);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(0, waypointOld.getManhattanDistance());
    }

    @Test
    void rotateRight3() {
        WaypointOld.moveFerryForward(waypointOld, 1);
        WaypointOld.rotateWaypointLeft(waypointOld, 270);
        WaypointOld.moveFerryForward(waypointOld, 1);
        assertEquals(20, waypointOld.getManhattanDistance());
    }
}