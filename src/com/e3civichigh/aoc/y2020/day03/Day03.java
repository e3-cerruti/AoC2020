package com.e3civichigh.aoc.y2020.day03;

import java.util.Arrays;

public class Day03 {
    public static void main(String[] args) {
        Forest forest = new Forest("input.txt");

        int[][] slopes =  {{1,1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        int product = 1;

        for (int[] slope : slopes) {
            int trees = forest.treesOnSlopeFor(slope);
            System.out.printf("%d trees on slope %s\n", trees, Arrays.toString(slope));
            product *= trees;
        }

        System.out.printf("\nProduct of trees on slopes: %d\n", product);
    }
}
