package day17;

import java.io.InputStream;
import java.util.Scanner;

public class Day17 {
    private final InputStream dataFile;
    private final Grid<Cube> grid = new Grid<>();
    private final Grid<Cube> hyperGrid = new Grid<>();

    public static void main(String[] args) {
        Day17 day = new Day17(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.initializeCubes();
        day.startup();
        System.out.format("Part 1: %d active cubes\n", day.activeCubes());
        System.out.format("Part 2: %d active hyper cubes\n", day.activeHyperCubes());

    }

    private long activeCubes() {
        return grid.activeCells();
    }

    private long activeHyperCubes() {
        return hyperGrid.activeCells();
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
                String key = Cube.toKey(new int[]{x, y, z});
                Cube cube = new Cube(grid, key);
                grid.getCellMap().put(key, cube);
                String hyperKey = Cube.toKey(new int[] { w, x, y, z});
                Cube hyperCube = new Cube(hyperGrid, hyperKey);
                hyperGrid.getCellMap().put(hyperKey, hyperCube);

                if (c == '#') {
                    cube.setFutureActive();
                    cube.wakeNeighbors();
                    hyperCube.setFutureActive();
                    hyperCube.wakeNeighbors();
                }
                x += 1;
            }
            y += 1;
        }
        grid.transition();
        hyperGrid.transition();
    }

    public void startup() {
        System.out.format("%d active cubes\n", activeCubes());
        System.out.format("%d active hyper cubes\n", activeHyperCubes());
        for (int cycle = 0; cycle < 6; cycle++) {
            grid.calculateNewState();
            grid.transition();
            hyperGrid.calculateNewState();
            hyperGrid.transition();
            System.out.format("%d active cubes\n", activeCubes());
            System.out.format("%d active hyper cubes\n", activeHyperCubes());

        }
    }
}
