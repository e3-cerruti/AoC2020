package com.e3civichigh.aoc.y2020.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Rule {
    private int number;
    private List<List<Integer>> subRules;
    private String value;

    public Rule(int number, String value) {
        this.number = number;
        this.value = value;
    }

    public Rule(int number, List<List<Integer>> ranges) {
        this.number = number;
        this.subRules = ranges;
    }

    public static Rule fromString(String line) {
        int number = Integer.parseInt(line.substring(0, line.indexOf(":")));
        String value;
        if (line.contains("\"")) {
            int pos = line.indexOf("\"") + 1;
            value = line.substring(pos, pos + 1);
            return new Rule(number, value);
        } else {
            List<List<Integer>> ranges = new ArrayList<>();
            String[] parts = line.substring(line.indexOf(":") + 1).split("\\|");
            for (String part: parts) {
                ranges.add(Arrays.stream(part.trim().split("\\s")).map(Integer::parseInt).collect(Collectors.toList()));
            }
            return new Rule(number, ranges);
        }
    }

    public int getNumber() {
        return number;
    }

    public String getValue() {
        return value;
    }

    public List<List<Integer>> getSubRules() {
        return subRules;
    }
}
