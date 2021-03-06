package com.e3civichigh.aoc.y2020.day03;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Forest {
    private int height;
    private List<String> lines;
    private int width;

    public Forest(String filename) {
        try {
            URL url = Day03.class.getResource(filename);
            if (url == null) {
                throw new FileNotFoundException();
            }

            lines = Files.readAllLines(Paths.get(url.toURI()));
            width = lines.get(0).length();
            height = lines.size();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return;
        }
    }

    public int treesOnSlopeWhile(int[] slope) {
        int trees = 0;
        int[] position = {0, 0};

        while (position[1] < height) {
            if (lines.get(position[1]).charAt(position[0] % width) == '#' ) {
                trees += 1;
            }
            position[0] += slope[0];
            position[1] += slope[1];
        }
        return trees;
    }

    public int treesOnSlopeFor(int[] slope) {
        int trees = 0;

        for (int y = 0; y < height; y += slope[1]) {
            int x = (y / slope[1]) * slope[0] % width;
            if (lines.get(y).charAt(x) == '#') {
                trees += 1;
            }
        }

        return trees;
    }
}
