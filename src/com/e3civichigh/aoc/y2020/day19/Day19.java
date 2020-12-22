package com.e3civichigh.aoc.y2020.day19;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    private final InputStream dataFile;
    private Map<Integer, Rule> rules = new HashMap<>();

    public Day19(String fileName) {
        dataFile = Day19.class.getResourceAsStream(fileName);
    }


    public static void main(String[] args) {
        Day19 day = new Day19(args.length >= 1 && args[0] != null ? args[0] : "day21/test.txt");
        day.processFile();
    }

    public void processFile() {
        try (Scanner input = new Scanner(dataFile)) {
            boolean done = false;
            while (!done) {
                String line = input.nextLine();
                if (line.isBlank()) {
                    done = true;
                } else {
                    Rule rule = Rule.fromString(line);
                    rules.put(rule.getNumber(), rule);
                }
            }

            String zeroString = String.join("|", fromRule(rules.get(0)));
            String fortyTwoString = null;
            String thirtyOneString = null;
            String part2String = null;
            if (rules.get(42) != null && rules.get(31) != null) {
                fortyTwoString = String.join("|", fromRule(rules.get(42)));
                thirtyOneString = String.join("|", fromRule(rules.get(31)));
                part2String = "(" + fortyTwoString + ")+(" + fortyTwoString + ")+(" + thirtyOneString + ")+";
            }

            int patternSize = fromRule(rules.get(42)).get(0).length();

            Pattern zero = Pattern.compile(zeroString);
            Pattern part2 = null;
            Pattern fortyTwo = null;
            Pattern thirtyOne = null;

            if (part2String != null) {
                part2 = Pattern.compile(part2String);
                fortyTwo = Pattern.compile(fortyTwoString);
                thirtyOne = Pattern.compile(thirtyOneString);
            }


            int part1MatchingMessages = 0;
            int part2MatchingMessages = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Matcher zeroMatcher = zero.matcher(line);
                Matcher part2Matcher = part2String != null ? part2.matcher(line) : null;
                if (zeroMatcher.matches()) {

                    part1MatchingMessages += 1;
                }
                if (part2String != null && part2Matcher.matches()) {
                    Matcher fortyTwoMatcher;
                    long fortyTwoMatches = 0;
                    for (int i = 0; i < line.length(); i += patternSize) {
                        fortyTwoMatcher = fortyTwo.matcher(line.substring(i, i + patternSize));
                        if (fortyTwoMatcher.matches()){
                            fortyTwoMatches += 1;
                        }
                    }
                    Matcher thirtyOneMatcher;
                    long thirtyOneMatches = 0;
                    for (int i = line.length() - patternSize; i >= 0; i -= patternSize) {
                        thirtyOneMatcher = thirtyOne.matcher(line.substring(i, i + patternSize));
                        if (thirtyOneMatcher.matches()){
                            thirtyOneMatches += 1;
                        }
                    }
                    System.out.format("%s %d %d\n", line, fortyTwoMatches, thirtyOneMatches);
                    if (fortyTwoMatches > thirtyOneMatches) {
                        part2MatchingMessages += 1;
                    }
                }
            }

            System.out.format("Part 1 matching messages: %d\n", part1MatchingMessages);
            System.out.format("Part 2 matching messages: %d\n", part2MatchingMessages);
        }
    }

    public List<String> fromRule(Rule rule) {
        List<String> result = new ArrayList<>();
        if (rule.getValue() != null) {
            return Collections.singletonList(rule.getValue());
        } else {
            for (List<Integer> s : rule.getSubRules()) {
                List<String> subruleList = Collections.singletonList("");
                for (Integer n : s) {
                    List<String> x = fromRule(rules.get(n));
                    List<String> y = new ArrayList<>(subruleList.size()*x.size());
                    for (String x1 : x) {
                        for (String r1 : subruleList) {
                            y.add(r1+x1);
                        }
                    }
                    subruleList = y;
                }
                result.addAll(subruleList);
            }
        }
        return result;
    }

}
