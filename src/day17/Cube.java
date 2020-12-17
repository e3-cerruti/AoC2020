package day17;

import java.util.*;

public class Cube extends Cell<Cube> {

    public Cube(Grid<Cube> grid, String coordinates) {
        super(grid, coordinates);
    }

    protected List<String> getNeighborKeys() {
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
            grid.wakeNeighbors(this);
        }
    }

    public Map<String, Cube> newNeighbors() {
        Map<String, Cube> result = new HashMap<>();

        for (String key : neighborKeys) {
            if (!grid.getCellMap().containsKey(key)) {
                result.put(key, new Cube(grid, key));
            }
        }
        return result;
    }

    public Map<String, Cube> wakeNeighbors() {
        HashMap<String, Cube> nextGeneration = new HashMap<>();
        for (String key : neighborKeys) {
            if (!grid.cellMap.containsKey(key)) {
                nextGeneration.put(key, new Cube(grid, key));
            }
        }
        return nextGeneration;
    }
}
