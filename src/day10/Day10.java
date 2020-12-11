package day10;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {
    private final List<Integer> adapters;
    private final HashMap<Integer, Long> nodeCache;

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
        nodeCache = new HashMap<>();
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

    private Map<Integer, ArrayList<Integer>> makeGraph() {
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();

        for (int adapter: adapters) {
            ArrayList<Integer> parents = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                if (adapters.contains(adapter + i)) {
                    parents.add(adapter + i);
                }
            }
            graph.put(adapter, parents);
        }
        return graph;
    }

    private void part2() {
        adapters.add(0, 0);  // add seat power

        Map<Integer, ArrayList<Integer>> graph = makeGraph();

        long paths = numberOfPaths(graph,0, Collections.max(adapters));
        System.out.format("Part 2: %d\n", paths);
    }

    public long numberOfPaths(Map<Integer, ArrayList<Integer>> graph, int source, int destination) {
        // to store required answer.
        List<Long> depthOfPath = new ArrayList<>(Collections.nCopies(Collections.max(adapters) + 1, 0L));

        // traverse in reverse order
        Collections.reverse(adapters);
        for (int adapter: adapters) {
            if (adapter == destination) {
                depthOfPath.set(adapter, 1L);
                continue;
            }
            long currentDepth = 0;
            for (int child: graph.get(adapter)) {
                currentDepth += depthOfPath.get(child);
            }
            depthOfPath.set(adapter, currentDepth);
        }

        return depthOfPath.get(source);
    }

    @SuppressWarnings("unused")
    private void part2r() {
        adapters.add(0, 0); // Insert the wall socket
        Map<Integer, ArrayList<Integer>> graph = makeGraph();

        long paths = numberOfPaths(graph, 0);
        System.out.format("Part 2: %d\n", paths);
    }

    private long numberOfPaths(Map<Integer, ArrayList<Integer>> graph, int node) {
        if (!graph.containsKey(node) || graph.get(node).size() == 0) return 1; // End of tree
        if (nodeCache.containsKey(node)) return nodeCache.get(node);

        long paths = 0;
        for (int child: graph.get(node)) {
            paths += numberOfPaths(graph, child);
        }
        nodeCache.put(node, paths);
        return paths;
    }
}
