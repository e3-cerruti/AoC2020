package com.e3civichigh.aoc.y2020.day12;

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
        waypoint.moveNorth(10);
        waypoint.moveForward(1);
        assertEquals(21, waypoint.getManhattanDistance());
    }

    @Test
    void moveeast() {
        waypoint.moveEast(10);
        waypoint.moveForward(1);
        assertEquals(21, waypoint.getManhattanDistance());
    }

    @Test
    void moveSouth() {
        waypoint.moveSouth(10);
        waypoint.moveForward(1);
        assertEquals(19, waypoint.getManhattanDistance());
    }

    @Test
    void movewest() {
        waypoint.moveWest(10);
        waypoint.moveForward(1);
        assertEquals(1, waypoint.getManhattanDistance());
    }

    @Test
    void rotateLeft() {
        waypoint.moveForward(1);
        waypoint.rotateLeft(90);
        waypoint.moveForward(1);
        assertEquals(20, waypoint.getManhattanDistance());
    }

    @Test
    void rotateLeft2() {
        waypoint.moveForward(1);
        waypoint.rotateLeft(180);
        waypoint.moveForward(1);
        assertEquals(0, waypoint.getManhattanDistance());
    }

    @Test
    void rotateLeft3() {
        waypoint.moveForward(1);
        waypoint.rotateLeft(270);
        waypoint.moveForward(1);
        assertEquals(20, waypoint.getManhattanDistance());
    }

    @Test
    void rotateRigth() {
        waypoint.moveForward(1);
        waypoint.rotateLeft(90);
        waypoint.moveForward(1);
        assertEquals(20, waypoint.getManhattanDistance());
    }

    @Test
    void rotateRight2() {
        waypoint.moveForward(1);
        waypoint.rotateLeft(180);
        waypoint.moveForward(1);
        assertEquals(0, waypoint.getManhattanDistance());
    }

    @Test
    void rotateRight3() {
        waypoint.moveForward(1);
        waypoint.rotateLeft(270);
        waypoint.moveForward(1);
        assertEquals(20, waypoint.getManhattanDistance());
    }
}