package com.e3civichigh.aoc.y2020.day15;

import java.io.InputStream;
import java.util.*;

public class Day15 {
    public static final int NTH = 30000000;
    private final InputStream dataFile;
    private final Map<Integer, Integer> numbers = new HashMap<>();
    private int nextNumber;

    /*
    Given the starting numbers 1,3,2, the 2020th number spoken is 1.
    Given the starting numbers 2,1,3, the 2020th number spoken is 10.
    Given the starting numbers 1,2,3, the 2020th number spoken is 27.
    Given the starting numbers 2,3,1, the 2020th number spoken is 78.
    Given the starting numbers 3,2,1, the 2020th number spoken is 438.
    Given the starting numbers 3,1,2, the 2020th number spoken is 1836.
     */
    public static void main(String[] args) {
        Day15 day = new Day15(args.length >= 1 && args[0] != null ? args[0] : "day21/test.txt");
        int start = day.processInput();
        day.startGame(start);
    }

    public Day15(String file) {
        dataFile = Day15.class.getResourceAsStream(file);
    }

    private int processInput() {
        Scanner input = new Scanner(dataFile);
        int i = 1;
        while (input.hasNextLine()) {
            int number = input.nextInt();
            nextNumber = numbers.containsKey(number) ? i - numbers.get(number) : 0;
            numbers.put(number, i);
            i++;
        }
        return i;
    }

    private void startGame(int start) {
        for (int i = start; i < NTH; i++) {
            int number = nextNumber;
            nextNumber = numbers.containsKey(number) ? i - numbers.get(number) : 0;
            numbers.put(number, i);
        }

        System.out.println(nextNumber);
    }
}
