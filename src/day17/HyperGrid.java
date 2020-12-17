package day17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HyperGrid {
    Map<String, HyperCube> cubeMap = new HashMap<>();
    private final Map<String, HyperCube> nextGeneration = new HashMap<>();

    public Map<String, HyperCube> getCubeMap() {
        return cubeMap;
    }

    public void wakeNeighbors(int w, int x, int y, int z) {
        // Set of neighbors to wake on new cycle
        for (int d = w - 1; d <= w + 1; d++) {
            for (int a = x - 1; a <= x + 1; a++) {
                for (int b = y - 1; b < y + 1; b++) {
                    for (int c = z - 1; c <= z + 1; c++) {
                        String key = HyperCube.mapKey(d, a, b, c);
                        if (!cubeMap.containsKey(key)) {
                            nextGeneration.put(key, new HyperCube(this, d, a, b, c));
                        }
                    }
                }
            }
        }
    }

    public void calculateNewState() {
        Map<String, HyperCube> newNeighbors = new HashMap<>();
        for (HyperCube cube : cubeMap.values()) {
            newNeighbors.putAll(cube.newNeighbors());
        }
        cubeMap.putAll(newNeighbors);
        cubeMap.values().forEach(HyperCube::calculateNewState);
    }

    public void transition() {
        cubeMap.values().forEach(HyperCube::transition);
        List<String> duplicates = nextGeneration.keySet().stream()
                .filter(k -> cubeMap.containsKey(k)).collect(Collectors.toList());
        duplicates.forEach(nextGeneration::remove);
        cubeMap.putAll(nextGeneration);
        nextGeneration.clear();
    }

    public long activeCubes() {
        return cubeMap.values().stream().filter(HyperCube::isActive).count();
    }
}
