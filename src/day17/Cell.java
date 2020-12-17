package day17;

import java.util.*;

public abstract class Cell {
    protected boolean active = false;
    protected boolean nextState = false;

    protected final List<String> neighborKeys;
    protected final int[] coordinates;
    protected final int dimensions;
    protected final Grid<? extends Cell> grid;

    public Cell(Grid<? extends Cell> grid, String coordinates) {
        this(grid, Arrays
                .stream(coordinates.replace("[", "").replace("]", "").split(", "))
                .mapToInt(Integer::parseInt)
                .toArray());
    }

    public Cell(Grid<? extends Cell> grid, int[] coordinates) {
        this.coordinates = coordinates;
        this.dimensions = coordinates.length;
        this.grid = grid;
        neighborKeys = neighborKeys();
    }

    protected abstract List<String> neighborKeys();

    public boolean isActive() {
        return active;
    }

    public void setFutureActive() {
        nextState = true;
    }

    public void transition() {
        active = nextState;
    }

    public abstract void calculateNewState();

    public abstract <T extends Cell> Map<String, T> newNeighbors();

    public abstract <T extends Cell> Map<String, T> wakeNeighbors();

    public static String toKey(int[] c) {
        return Arrays.toString(c);
    }

    public long getActiveNeighbors() {
        return neighbors().stream().filter(Cell::isActive).count();
    }

    public List<Cell> neighbors() {
        List<Cell> result = new ArrayList<>();

        for (String key : neighborKeys) {
            if (grid.getCellMap().containsKey(key)) {
                result.add(grid.getCellMap().get(key));
            }
        }
        return result;
    }
}
