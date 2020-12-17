package day17;

import java.util.*;
import java.util.stream.Collectors;

public class Grid {
    Map<String, Cube> cubeMap = new HashMap<>();
    private final Map<String, Cube> nextGeneration = new HashMap<>();

    public Map<String, Cube> getCubeMap() {
        return cubeMap;
    }

    public void wakeNeighbors(int x, int y, int z) {
        // Set of neighbors to wake on new cycle
        for (int a = x - 1; a <= x + 1; a++) {
            for (int b = y - 1; b < y + 1; b++) {
                for (int c = z - 1; c <= z + 1; c++) {
                    String key = Cube.mapKey(a, b, c);
                    if (!cubeMap.containsKey(key)) {
                        nextGeneration.put(key, new Cube(this, a, b, c));
                    }
                }
            }
        }
    }

    public void calculateNewState() {
        Map<String, Cube> newNeighbors = new HashMap<>();
        for (Cube cube : cubeMap.values()) {
            newNeighbors.putAll(cube.newNeighbors());
        }
        cubeMap.putAll(newNeighbors);
        cubeMap.values().forEach(Cube::calculateNewState);
    }

    public void transition() {
        cubeMap.values().forEach(Cube::transition);
        List<String> duplicates = nextGeneration.keySet().stream()
                .filter(k -> cubeMap.containsKey(k)).collect(Collectors.toList());
        duplicates.forEach(nextGeneration::remove);
        cubeMap.putAll(nextGeneration);
        nextGeneration.clear();
    }

    public long activeCubes() {
        return cubeMap.values().stream().filter(Cube::isActive).count();
    }
}
