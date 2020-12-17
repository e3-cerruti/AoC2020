package day17;

import java.util.*;

public class Cube {
    private final int[] coordinates;
    private final int dimensions;
    private final Grid grid;
    private final List<String> neighborKeys;
    private boolean active;
    private boolean nextState = false;

    public Cube(Grid grid, String coordinates) {
        this(grid, Arrays
                .stream(coordinates.replace("[", "").replace("]", "").split(", "))
                .mapToInt(Integer::parseInt)
                .toArray());
    }

    public Cube(Grid grid, int[] coordinates) {
        this.coordinates = coordinates;
        this.dimensions = coordinates.length;
        this.active = false;
        this.grid = grid;
        neighborKeys = neighborKeys();
    }

    private List<String> neighborKeys() {
        List<String> result = new ArrayList<>();
        int numberNeighbors = (int) Math.pow(3, dimensions);
        int self = numberNeighbors / 2;

        for (int i = 0; i < numberNeighbors; i++) {
            if (i == self) continue;

            int[] c = new int[dimensions];
            for (int j = 0; j < dimensions; j++) {
                c[j] = coordinates[j] + i / (int) Math.pow(3, j) % 3 - 1;
            }
            result.add(Cube.toKey(c));
        }
        return result;
    }

    public static String toKey(int[] c) {
        return Arrays.toString(c);
    }

    public void transition() {
        active = nextState;
    }

    public void calculateNewState() {
        long activeNeighbors = getActiveNeighbors();
        // If a cube is active and exactly 2 or 3 of its neighbors are also active,
        // the cube remains active. Otherwise, the cube becomes inactive.
        if (active && (activeNeighbors != 2 && activeNeighbors != 3)) {
            nextState = false;
        }

        // If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active.
        // Otherwise, the cube remains inactive.
        else if (!active && activeNeighbors == 3) {
            nextState = true;
            wakeNeighbors();
        }
    }

    public long getActiveNeighbors() {
        return neighbors().stream().filter(Cube::isActive).count();
    }

    public boolean isActive() {
        return active;
    }

    public List<Cube> neighbors() {
        List<Cube> result = new ArrayList<>();

        for (String key : neighborKeys) {
            if (grid.getCubeMap().containsKey(key)) {
                result.add(grid.getCubeMap().get(key));
            }
        }
        return result;
    }

    public void setFutureActive() {
        nextState = true;
    }

    public Map<String, Cube> newNeighbors() {
        Map<String, Cube> result = new HashMap<>();

        for (String key : neighborKeys) {
            if (!grid.getCubeMap().containsKey(key)) {
                result.put(key, new Cube(grid, key));
            }
        }
        return result;
    }

    public void wakeNeighbors() {
        HashMap<String, Cube> nextGeneration = new HashMap<>();
        for (String key : neighborKeys) {
            if (!grid.cubeMap.containsKey(key)) {
                nextGeneration.put(key, new Cube(grid, key));
            }
        }
        if (nextGeneration.size() > 0) grid.addNextGeneration(nextGeneration);
    }
}
