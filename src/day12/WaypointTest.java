package day12;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaypointTest {

    private Waypoint waypoint;

    @BeforeEach
    void setUp() {
        waypoint = new Waypoint();
    }

    @Test
    void moveNorth() {
        Waypoint.moveFerryNorth(waypoint, 10);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(21, waypoint.getManhattanDistance());
    }

    @Test
    void moveEast() {
        Waypoint.moveFerryEast(waypoint, 10);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(21, waypoint.getManhattanDistance());
    }

    @Test
    void moveSouth() {
        Waypoint.moveFerrySouth(waypoint, 10);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(19, waypoint.getManhattanDistance());
    }

    @Test
    void moveWest() {
        Waypoint.moveFerryWest(waypoint, 10);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(1, waypoint.getManhattanDistance());
    }

    @Test
    void rotateLeft() {
        Waypoint.moveFerryForward(waypoint, 1);
        Waypoint.rotateWaypointLeft(waypoint, 90);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(20, waypoint.getManhattanDistance());
    }

    @Test
    void rotateLeft2() {
        Waypoint.moveFerryForward(waypoint, 1);
        Waypoint.rotateWaypointLeft(waypoint, 180);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(0, waypoint.getManhattanDistance());
    }

    @Test
    void rotateLeft3() {
        Waypoint.moveFerryForward(waypoint, 1);
        Waypoint.rotateWaypointLeft(waypoint, 270);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(20, waypoint.getManhattanDistance());
    }

    @Test
    void rotateRigth() {
        Waypoint.moveFerryForward(waypoint, 1);
        Waypoint.rotateWaypointLeft(waypoint, 90);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(20, waypoint.getManhattanDistance());
    }

    @Test
    void rotateRight2() {
        Waypoint.moveFerryForward(waypoint, 1);
        Waypoint.rotateWaypointLeft(waypoint, 180);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(0, waypoint.getManhattanDistance());
    }

    @Test
    void rotateRight3() {
        Waypoint.moveFerryForward(waypoint, 1);
        Waypoint.rotateWaypointLeft(waypoint, 270);
        Waypoint.moveFerryForward(waypoint, 1);
        assertEquals(20, waypoint.getManhattanDistance());
    }
}