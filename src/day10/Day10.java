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
    public static final int REPETION = 100;
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

        day.adapters.add(0, 0);  // add seat power
        Map<Integer, ArrayList<Integer>> graph = day.makeGraph();

        //day.part2(graph);
        //day.part2r(graph);
        //day.part2rnc(graph);

        long mark1 = System.nanoTime();
        for (int i = 0; i < REPETION; i++) day.part2(graph);
        long mark2 = System.nanoTime();
        for (int i = 0; i < REPETION; i++) {
            day.part2r(graph);
            day.nodeCache.clear();
        }
        long mark3 = System.nanoTime();
        for (int i = 0; i < REPETION; i++) day.part2rnc(graph);
        long mark4 = System.nanoTime();

        System.out.format("Non-recursive: %d ns\n", (mark2 - mark1) / REPETION);
        System.out.format("Recursive: %d ns\n", (mark3 - mark2) / REPETION);
        System.out.format("Non-cached recursive: %d ns\n", (mark4 - mark3) / REPETION);
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

    private long part2(Map<Integer, ArrayList<Integer>> graph) {
        return numberOfPaths(graph,0, Collections.max(adapters));
    }

    public long numberOfPaths(Map<Integer, ArrayList<Integer>> graph, int source, int destination) {
        // to store required answer.
        long[] depthOfPath = new long[destination+1];
        // Normally you would need to do a topological sort here
        List<Integer> nodes = new ArrayList<>(graph.keySet());
        nodes.sort(Collections.reverseOrder());

        nodes.remove(0);
        depthOfPath[destination] = 1L;

        for (int node: nodes) {
            long currentDepth = 0;
            for (int child : graph.get(node)) {
                currentDepth += depthOfPath[child];
            }
            depthOfPath[node] = currentDepth;
        }

        return depthOfPath[source];
    }

    @SuppressWarnings("unused")
    private long part2r(Map<Integer, ArrayList<Integer>> graph) {
        return numberOfPathsRecursive(graph, 0);
    }

    private long numberOfPathsRecursive(Map<Integer, ArrayList<Integer>> graph, int node) {
        if (!graph.containsKey(node) || graph.get(node).size() == 0) return 1; // End of tree
        if (nodeCache.containsKey(node)) return nodeCache.get(node);

        long paths = 0;
        for (int child: graph.get(node)) {
            paths += numberOfPathsRecursive(graph, child);
        }
        nodeCache.put(node, paths);
        return paths;
    }

    @SuppressWarnings("unused")
    private long part2rnc(Map<Integer, ArrayList<Integer>> graph) {
        return numberOfPathsRecursiveNoCache(graph, 0);
    }

    private long numberOfPathsRecursiveNoCache(Map<Integer, ArrayList<Integer>> graph, int node) {
        if (!graph.containsKey(node) || graph.get(node).size() == 0) return 1; // End of tree

        long paths = 0;
        for (int child: graph.get(node)) {
            paths += numberOfPathsRecursiveNoCache(graph, child);
        }
        return paths;
    }

}
