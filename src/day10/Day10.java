package day10;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    private final List<Integer> adapters;

    public Day10(String inputFile) {
        List<Integer> input = null;
        try {
            URL url = Day10.class.getResource(inputFile);
            if (url == null) {
                throw new FileNotFoundException();
            }

            input = Files.readAllLines(Paths.get(url.toURI()))
                    .stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        adapters = input;
        if (adapters == null) {
            throw new RuntimeException("Unable to initialize Day 10.");
        }
    }

    public static void main(String[] args) {
        Day10 day = new Day10("input.txt");

        day.part1();
        day.part2();
    }

    private void part1() {
        HashMap<Integer, Integer> gaps = new HashMap<>();
        gaps.put(3, 1); // Add the last connection to the device

        int jolts = 0; // Socket relative joltage

        for (int adapter: this.adapters) {
            int gap = adapter - jolts;
            gaps.compute(gap, (k,v) -> v == null ? 1 : v+1);
            jolts = adapter;
        }

        System.out.format("Part 1: %d\n", gaps.get(1) * gaps.get(3));
    }

    private void part2() {
        HashMap<Integer, ArrayList<Integer>> tree = new HashMap<>();
        adapters.add(0, 0); // Insert the wall socket
        for (int adapter: adapters) {
            ArrayList<Integer> possibilities = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                if (adapters.contains(adapter + i)) {
                    possibilities.add(adapter + i);
                }
            }
            tree.put(adapter, possibilities);
        }

        int paths = numberOfPaths(tree, 0);
        System.out.format("Part 2: %d\n", paths);
    }

    private int numberOfPaths(HashMap<Integer, ArrayList<Integer>> tree, int node) {
        if (!tree.containsKey(node) || tree.get(node).size() == 0) return 1; // End of tree

        int paths = 0;
        for (int child: tree.get(node)) {
            paths += numberOfPaths(tree, child);
        }
        return paths;
    }
}
