package com.e3civichigh.aoc.y2020.day05;

import java.io.InputStream;
import java.util.*;

public class Rewrite {
    public static void main(String[] args) {
//        InputStream dataFile = Day05.class.getResourceAsStream("test.txt");
        InputStream dataFile = Rewrite.class.getResourceAsStream("input.txt");

        int lastOccupiedSeat = Integer.MIN_VALUE;
        int firstOccupiedSeat = Integer.MAX_VALUE;
        int sumOfOccupiedSeats = 0;

        List<Integer> someoneBesideMe = new ArrayList<Integer>(Arrays.asList(new Integer[(int) (Math.pow(2.0D, 10.0D) - 1)]));
        Collections.fill(someoneBesideMe, 0);
        SegmentManager manager = new SegmentManager();

        try (Scanner input = new Scanner(dataFile)) {
            while (input.hasNextLine()) {
                // Read a line
                String pass = input.nextLine();

                // Convert to binary string
                pass = pass
                        .replaceAll("F", "0")
                        .replaceAll("B", "1")
                        .replaceAll("L", "0")
                        .replaceAll("R", "1");

                // Convert to decimal representation
                int seatId = Integer.parseInt(pass, 2);

                // Find minimum and maximum ID
                firstOccupiedSeat = Math.min(firstOccupiedSeat, seatId);
                lastOccupiedSeat = Math.max(lastOccupiedSeat, seatId);
                sumOfOccupiedSeats += seatId;
            }
            System.out.format("Highest seat id: %d\n", lastOccupiedSeat);
            int mySeat = (lastOccupiedSeat - firstOccupiedSeat + 1) /2 * (firstOccupiedSeat + lastOccupiedSeat) - sumOfOccupiedSeats;
            System.out.format("My seat is: %d\n", mySeat);
        }
    }
}
