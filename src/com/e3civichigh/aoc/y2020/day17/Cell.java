package com.e3civichigh.aoc.y2020.day17;

import java.util.*;

public abstract class Cell<T extends Cell<T>> {
    protected boolean active = false;
    protected boolean nextState = false;

    protected final List<String> neighborKeys;
    protected final int[] coordinates;
    protected final int dimensions;
    protected final Grid<T> grid;

    public Cell(Grid<T> grid, String coordinates) {
        this(grid, Arrays
                .stream(coordinates.replace("[", "").replace("]", "").split(", "))
                .mapToInt(Integer::parseInt)
                .toArray());
    }

    public Cell(Grid<T> grid, int[] coordinates) {
        this.coordinates = coordinates;
        this.dimensions = coordinates.length;
        this.grid = grid;
        neighborKeys = getNeighborKeys();
    }

    protected abstract List<String> getNeighborKeys();

    public abstract void calculateNewState();

    public abstract Map<String, T> newNeighbors();

    public abstract Map<String, T> wakeNeighbors();

    public static String toKey(int[] c) {
        return Arrays.toString(c);
    }

    public boolean isActive() {
        return active;
    }

    public void setFutureActive() {
        nextState = true;
    }

    public void setFutureSuspended() {
        nextState = false;
    }

    public void transition() {
        active = nextState;
    }

    public long getActiveNeighborCount() {
        return neighbors().stream().filter(Cell::isActive).count();
    }

    public List<T> neighbors() {
        List<T> result = new ArrayList<>();

        for (String key : neighborKeys) {
            if (grid.getCellMap().containsKey(key)) {
                result.add(grid.getCellMap().get(key));
            }
        }
        return result;
    }

}
