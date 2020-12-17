package day17;

import java.io.InputStream;
import java.util.Scanner;

public class Day17 {
    private final InputStream dataFile;
    private final Grid grid = new Grid();
    private final HyperGrid hgrid = new HyperGrid();

    public static void main(String[] args) {
        Day17 day = new Day17(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.initializeCubes();
        day.startup();
        System.out.format("Part 1: %d active cubes\n", day.activeCubes());
        System.out.format("Part 2: %d active hyper cubes\n", day.activeHyperCubes());

    }

    private long activeCubes() {
        return grid.activeCubes();
    }

    private long activeHyperCubes() {
        return hgrid.activeCubes();
    }

    public Day17(String file) {
        dataFile = Day17.class.getResourceAsStream(file);
    }

    public void initializeCubes(){
        Scanner input = new Scanner(dataFile);
        int w = 0, x, y = 0, z = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            x = 0;
            for (char c : line.toCharArray()) {
                String key = Cube.mapKey(x, y, z);
                Cube cube = new Cube(grid, x, y, z);
                grid.getCubeMap().put(key, cube);
                String hkey = HyperCube.mapKey(w, x, y, z);
                HyperCube hcube = new HyperCube(hgrid, w, x, y, z);
                hgrid.getCubeMap().put(hkey, hcube);

                if (c == '#') {
                    cube.setFutureActive();
                    cube.wakeNeighbors();
                    hcube.setFutureActive();
                    hgrid.wakeNeighbors(w, x, y, z);
                }
                x += 1;
            }
            y += 1;
        }
        grid.transition();
        hgrid.transition();
    }

    public void startup() {
        System.out.format("%d active hyper cubes\n", activeHyperCubes());
        for (int cycle = 0; cycle < 6; cycle++) {
            grid.calculateNewState();
            grid.transition();
            hgrid.calculateNewState();
            hgrid.transition();
            System.out.format("%d active hyper cubes\n", activeHyperCubes());

        }
    }
}
