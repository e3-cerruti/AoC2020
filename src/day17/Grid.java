package day17;

import java.util.*;
import java.util.stream.Collectors;

public class Grid<T extends Cell> {
    Map<String, T> cellMap = new HashMap<>();
    private final Map<String, T> nextGeneration = new HashMap<>();

    public Map<String, T> getCellMap() {
        return cellMap;
    }

    public void calculateNewState() {
        Map<String, T> newNeighbors = new HashMap<>();
        for (T cell : cellMap.values()) {
            newNeighbors.putAll(cell.newNeighbors());
        }
        cellMap.putAll(newNeighbors);
        cellMap.values().forEach(T::calculateNewState);
    }

    public void transition() {
        cellMap.values().forEach(T::transition);
        List<String> duplicates = nextGeneration.keySet().stream()
                .filter(k -> cellMap.containsKey(k)).collect(Collectors.toList());
        duplicates.forEach(nextGeneration::remove);
        cellMap.putAll(nextGeneration);
        nextGeneration.clear();
    }

    public long activeCells() {
        return cellMap.values().stream().filter(T::isActive).count();
    }

    public void addNextGeneration(Map<String, T> nextGeneration) {
        this.nextGeneration.putAll(nextGeneration);
    }

    public void wakeNeighbors(Cell cell) {
        addNextGeneration(cell.wakeNeighbors());
    }
}
