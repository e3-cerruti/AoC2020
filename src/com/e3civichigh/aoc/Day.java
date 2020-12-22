package com.e3civichigh.aoc;

import com.e3civichigh.aoc.y2020.day20.Day20;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Day {

    public List<String> getInputList(String[] args) throws AdventOfCodeException {
        String fileName = args.length >= 1 && args[0] != null ? args[0] : "test.txt";
        URL url = this.getClass().getResource(fileName);

        List<String> lines;
        try {
            if (url == null) {
                throw new FileNotFoundException(fileName);
            }
            lines = Files.readAllLines(Paths.get(url.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new AdventOfCodeException(e);
        }

        return lines;
    }

    public InputStream getInputStream(String[] args) {
        String fileName = args.length >= 1 && args[0] != null ? args[0] : "test.txt";

        return this.getClass().getResourceAsStream(fileName);
    }

    public long runPart1() {
        return 0;
    }

    public long runPart2() {
        return 0;
    }

    protected class AdventOfCodeException extends Throwable {
        public AdventOfCodeException(Exception e) {
            super(e);
        }
    }
}
