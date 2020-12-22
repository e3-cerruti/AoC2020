package com.e3civichigh.aoc.y2020.day11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day11 {
    private char[][] waitingArea;
    public static final int[][] VECTORS = new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    public Day11(String inputFile) {
        try {
            URL url = Day11.class.getResource(inputFile);
            if (url == null) {
                throw new FileNotFoundException();
            }

            List<String> rows = Files.readAllLines(Paths.get(url.toURI()));
            this.waitingArea = new char[rows.size()][rows.get(0).length()];

            for (int i = 0; i < rows.size(); i++) {
                waitingArea[i] = rows.get(i).toCharArray();
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            System.exit(1);
        }
        printWaitingArea();
    }

    private void printWaitingArea() {
        for (char[] row : waitingArea) {
            for (char seat : row) {
                System.out.print(seat);
            }
            System.out.println();
        }
    }

    private boolean turn() {
        char [][] newWaitingArea = new char[waitingArea.length][];
        for(int i = 0; i < waitingArea.length; i++)
            newWaitingArea[i] = waitingArea[i].clone();

        boolean modified = false;
        for (int row = 0; row < waitingArea.length; row++) {
            for (int seat = 0; seat < waitingArea[row].length; seat++) {
                int occupied = observePart2(row, seat);
                // If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
                if (waitingArea[row][seat] == 'L' && occupied == 0) {
                    newWaitingArea[row][seat] = '#';
                    modified = true;
                }
                // If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
                else if (waitingArea[row][seat] == '#' && occupied >= 5) {
                    newWaitingArea[row][seat] = 'L';
                    modified = true;
                }
                // Otherwise, the seat's state does not change.
            }
        }
        waitingArea = newWaitingArea;

        return modified;
    }

    public static void main(String[] args) {
        Day11 day = new Day11("input.txt");

        int iterations = 1;
        while (day.turn()) {
            iterations += 1;
            if (iterations < 5) day.printWaitingArea();
            System.out.println();
        }
        System.out.println(day.occupiedSeats());
    }

    @SuppressWarnings("unused")
    private int observePart1(int inputRow, int inputSeat) {
        int result = 0;
        int minRow = Math.max(inputRow - 1, 0);
        int maxRow = Math.min(inputRow + 2, waitingArea.length);
        int minSeat = Math.max(inputSeat - 1, 0);
        int maxSeat = Math.min(inputSeat + 2, waitingArea[inputRow].length);

        for (int row = minRow; row < maxRow; row++) {
            for (int seat = minSeat; seat < maxSeat; seat++) {
                if ((row != inputRow || seat != inputSeat) && waitingArea[row][seat] == '#') result++;
            }
        }

        return result;
    }

    private int observePart2(int inputRow, int inputSeat) {
        int result = 0;

        for (int[] vector: VECTORS) {
            boolean satisfied = false;
            int row = inputRow + vector[0];
            int seat = inputSeat + vector[1];
            while (!satisfied && inBounds(row, seat)) {
                if (waitingArea[row][seat] == '#') {
                    satisfied = true;
                    result++;
                } else if (waitingArea[row][seat] == 'L') {
                    satisfied = true;
                } else {
                    row = row + vector[0];
                    seat = seat + vector[1];
                }
            }
        }

        return result;
    }

    private boolean inBounds(int row, int seat) {
        return 0 <= row && row < waitingArea.length
                && 0 <= seat && seat < waitingArea[row].length;
    }

    private int occupiedSeats() {
        int occupiedSeats = 0;
        for (char[] chars : waitingArea) {
            for (char aChar : chars) {
                if (aChar == '#') occupiedSeats++;
            }
        }
        return occupiedSeats;
    }

}
