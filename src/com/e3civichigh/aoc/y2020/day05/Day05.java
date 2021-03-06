package com.e3civichigh.aoc.y2020.day05;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Day05 {
    public static void main(String[] args) {
//        InputStream dataFile = Day05.class.getResourceAsStream("test.txt");
        InputStream dataFile = Day05.class.getResourceAsStream("input.txt");

        int result = Integer.MIN_VALUE;
        ArrayList<Integer> seatList = new ArrayList<>();
        SegmentManager manager = new SegmentManager();

        try (Scanner input = new Scanner(dataFile)) {
            while (input.hasNextLine()) {
                // Read a line
                String pass = input.nextLine();

                // Split the row and seat
                String row = pass.substring(0, 7);
                String seatNumber = pass.substring(7);

                // Convert to binary string
                row = row
                        .replaceAll("F", "0")
                        .replaceAll("B", "1");
                seatNumber = seatNumber
                        .replaceAll("L", "0")
                        .replaceAll("R", "1");

                // Convert to decimal representation
                int rowValue = Integer.parseInt(row, 2);
                int seatValue = Integer.parseInt(seatNumber, 2);

                // Compute Seat ID
                int seatId = rowValue * 8 + seatValue;
//                System.out.println(rowValue + " " + seatValue + " " + seatId);

                // Find maximum ID
                result = Math.max(result, seatId);

                manager.addSeat(seatId);
                seatList.add(seatId);
            }
            System.out.format("Highest seat id: %d\n", result);
            System.out.format("My seat is: %d\n", manager.getSeat());

            seatList.sort(Integer::compareTo);

            int previousSeat = Integer.MIN_VALUE;
            for (int assignedSeat : seatList) {
                if (previousSeat == Integer.MIN_VALUE) {
                    previousSeat = assignedSeat;
                    continue;
                }
                if (assignedSeat > previousSeat + 1) {
                    // Gap in seat assignments
                    System.out.format("Sorting says my seat is: %d\n", previousSeat + 1);
                }
                previousSeat = assignedSeat;
            }
        }
    }
}
