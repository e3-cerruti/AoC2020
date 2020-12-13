package day12;

import java.util.function.BiConsumer;

public enum Direction {
    NORTH (0, Ferry::moveNorth, new int[][]{new int[]{1,0}, new int[]{0,1}}),
    EAST (90, Ferry::moveEast, new int[][]{new int[]{0,1}, new int[]{-1, 0}}),
    SOUTH (180, Ferry::moveSouth, new int[][]{new int[]{-1,0}, new int[]{0,-1}}),
    WEST (270, Ferry::moveWest, new int[][]{new int[]{0,-1}, new int[]{1, 0}});

    private final int degrees;
    private final BiConsumer<Ferry, Integer> handler;
    private final int[][] rotationMatrix;

    Direction(int degrees, BiConsumer<Ferry, Integer> handler, int[][] rotationMatrix) {
        this.degrees = degrees;
        this.handler = handler;
        this.rotationMatrix = rotationMatrix;
    }

    public Direction turnLeft(int angle) {
        return Direction.fromDegrees((degrees - (angle % 360) + 360) % 360);
    }

    public Direction turnRight(int angle) {
        return Direction.fromDegrees((degrees + angle) % 360);
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

    static Direction fromDegrees(int angle) {
        for (Direction v : values()) {
            if (v.degrees == angle) {
                return v;
            }
        }
        return null; // TODO: Should this throw an exception?
    }

    public void move(Ferry ferry, int distance) {
        handler.accept(ferry, distance);
    }
}
