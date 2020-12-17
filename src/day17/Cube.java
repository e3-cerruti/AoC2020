package day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cube {
    private final int x;
    private final int y;
    private final int z;

    private boolean active;
    private boolean nextState = false;

    private final Grid grid;

    public Cube(Grid grid, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.active = false;
        this.grid = grid;
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
            grid.wakeNeighbors(x, y, z);
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

        for (int a = x - 1; a <= x + 1; a++) {
            for (int b = y - 1; b <= y + 1; b++) {
                for (int c = z - 1; c <= z + 1; c++) {
                    if (a == x && b == y && c == z) continue;
                    String key = mapKey(a, b, c);
                    if (grid.getCubeMap().containsKey(key)) {
                        result.add(grid.getCubeMap().get(key));
                    }
                }
            }
        }

        return result;
    }

    public static String mapKey(int x, int y, int z) {
        return String.join(".", Integer.toString(x), Integer.toString(y), Integer.toString(z));
    }

    public void setFutureActive() {
        nextState = true;
    }

    public Map<String, Cube> newNeighbors() {
        Map<String, Cube> result = new HashMap<>();

        for (int a = x - 1; a <= x + 1; a++) {
            for (int b = y - 1; b <= y + 1; b++) {
                for (int c = z - 1; c <= z + 1; c++) {
                    if (a == x && b == y && c == z) continue;
                    String key = mapKey(a, b, c);
                    if (!grid.getCubeMap().containsKey(key)) {
                        result.put(key, new Cube(grid, a, b, c));
                    }
                }
            }
        }

        return result;
    }
}
