package day17;

import java.util.*;
import java.util.stream.Collectors;

public class Grid {
    Map<String, Cube> cubeMap = new HashMap<>();
    private final Map<String, Cube> nextGeneration = new HashMap<>();

    public Map<String, Cube> getCubeMap() {
        return cubeMap;
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

    public void addNextGeneration(HashMap<String, Cube> nextGeneration) {
        this.nextGeneration.putAll(nextGeneration);
    }
}
