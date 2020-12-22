package com.e3civichigh.aoc.y2020.day17;

import java.util.*;

public class Cube extends Cell<Cube>  {

    private final RuleSet<Cube> rules = new RuleSet<>();

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
        rules.apply(this);

        if (nextState) {
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
