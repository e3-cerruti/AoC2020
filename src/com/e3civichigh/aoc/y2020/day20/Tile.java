package com.e3civichigh.aoc.y2020.day20;

import java.util.*;

public class Tile {
    private static final int TOP = 0;
    private static final int RIGHT = 1;
    private static final int BOTTOM = 2;
    private static final int LEFT = 3;


    private final int tileNumber;
    private final int[] edge = new int[4];
    private final int[] reverse = new int[4];
    private final Map<Integer, List<Tile>> edgeMap = new HashMap<>(4);
    private final List<String> imageData;
    private int rotations = 0;
    private int horizontalFlip = 0;
    private int verticalFlip = 0;

    public Tile(String id, List<String> tileRows) {
        tileNumber = Integer.parseInt(id.substring(id.indexOf(" ") + 1, id.length() - 1));
        imageData = tileRows;

        List<String> rowValues = new ArrayList<>(tileRows.size());
        for (String tileRow : tileRows) {
            rowValues.add(tileRow
                    .replaceAll("#", "1")
                    .replaceAll("\\.", "0"));
        }

        int wordLength = rowValues.size();
        edge[TOP] = Integer.parseInt(rowValues.get(0), 2);
        reverse[TOP] = Integer.parseInt(new StringBuilder(rowValues.get(0)).reverse().toString(), 2);
        reverse[BOTTOM] = Integer.parseInt(rowValues.get(wordLength - 1), 2);
        edge[BOTTOM] = Integer.parseInt(new StringBuilder(rowValues.get(wordLength - 1)).reverse().toString(), 2);

        StringBuilder leftString = new StringBuilder();
        StringBuilder rightString = new StringBuilder();

        for (int i = 0; i < wordLength; i++) {
            String row = rowValues.get(i);
            leftString.append(row.charAt(0));
            rightString.append(row.charAt(wordLength - 1));
        }
        edge[RIGHT] = Integer.parseInt(rightString.toString(), 2);
        reverse[RIGHT] = Integer.parseInt(rightString.reverse().toString(), 2);
        reverse[LEFT] = Integer.parseInt(leftString.toString(), 2);
        edge[LEFT] = Integer.parseInt(leftString.reverse().toString(), 2);
    }

    public static void main(String[] args) {
        Tile testTile = new Tile("3079", Arrays.asList("..........",
                ".########.",
                ".#........",
                ".#........",
                ".#........",
                ".#####....",
                ".#........",
                ".#........",
                ".#........",
                ".#........"));

        testTile.dump();
//        for (int i = 0; i < 4; i++) {
//            System.out.println("----- " + i + " -----");
//            testTile.rotateClockwise();
//            testTile.dump();
//        }
        testTile.flipVertical();
        testTile.dump();
        testTile.flipHorizontal();
        testTile.dump();
        testTile.rotateClockwise();
        testTile.rotateClockwise();
        testTile.dump();

    }

    private void dump() {
        char[][] image = getImageData();
        for (char[] line : image) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public char[][] getImageData() {
        char[][] charData = new char[imageData.size()-2][imageData.size()-2];

        for (int i = 0; i < imageData.size() - 2 ; i++) {
            char[] data = imageData.get(i+1).toCharArray();
            charData[i] = Arrays.copyOfRange(data, 1, imageData.size() - 1);
        }

        if (rotations == 1) {
            char[][] rotatedData = new char[imageData.size() - 2][imageData.size() - 2];
            for (int row = 0; row < charData.length; row++) {
                for (int col = 0; col < charData.length; col++) {
                    rotatedData[col][charData.length - (row + 1)] = charData[row][col];
                }
            }
            charData = rotatedData;
        } else if (rotations == 2) {
            char[][] rotatedData = new char[imageData.size()-2][imageData.size()-2];

            for (int row = 0; row < charData.length; row++) {
                for (int col = 0; col < charData.length; col++) {
                    rotatedData[charData.length-(row + 1)][charData.length-(col + 1)] = charData[row][col];
                }
            }
            charData = rotatedData;
        } else if (rotations == 3) {
            char[][] rotatedData = new char[imageData.size()-2][imageData.size()-2];
            for (int row = 0; row < charData.length; row++) {
                for (int col = 0; col < charData.length; col++) {
                    rotatedData[charData.length-(col + 1)][row] = charData[row][col];
                }
            }
            charData = rotatedData;
        }

        if (horizontalFlip == 1 && verticalFlip == 1) {
            char[][] rotatedData = new char[imageData.size()-2][imageData.size()-2];

            for (int row = 0; row < charData.length; row++) {
                for (int col = 0; col < charData.length; col++) {
                    rotatedData[charData.length-(row + 1)][charData.length-(col + 1)] = charData[row][col];
                }
            }
            charData = rotatedData;
        } else if (horizontalFlip == 1) {
            char[][] rotatedData = new char[imageData.size()-2][imageData.size()-2];

            for (int row = 0; row < charData.length; row++) {
                for (int col = 0; col < charData.length; col++) {
                    rotatedData[row][charData.length-(col + 1)] = charData[row][col];
                }
            }
            charData = rotatedData;
        } else if (verticalFlip == 1) {
            char[][] rotatedData = new char[imageData.size() - 2][imageData.size() - 2];

            for (int row = 0; row < charData.length; row++) {
                System.arraycopy(charData[row], 0, rotatedData[charData.length - (row + 1)], 0, charData.length);
            }
            charData = rotatedData;
        }

        System.out.format("r:%d h:%d v:%d\n", rotations, horizontalFlip, verticalFlip);

        return charData;
    }

    public static int reverseBits(int n) {
        int rev = 0;

        // traversing bits of 'n'
        // from the right
        while (n > 0) {
            // bitwise left shift
            // 'rev' by 1
            rev <<= 1;

            // if current bit is '1'
            if ((n & 1) == 1)
                rev ^= 1;

            // bitwise right shift
            //'n' by 1
            n >>= 1;
        }
        // required number
        return rev;
    }

    public void matchEdge(Tile other) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (edge[i] == other.edge[j] || reverse[i] == other.edge[j]) {
                    edgeMap.computeIfAbsent(i, k -> new ArrayList<>()).add(other);
                    other.edgeMap.computeIfAbsent(j, k -> new ArrayList<>()).add(this);
                }
            }
        }
    }

    public void rotateClockwise() {
        rotations += 1;
        int temp = edge[TOP];
        edge[TOP] = edge[LEFT];
        edge[LEFT] = edge[BOTTOM];
        edge[BOTTOM] = edge[RIGHT];
        edge[RIGHT] = temp;
        temp = reverse[TOP];
        reverse[TOP] = reverse[LEFT];
        reverse[LEFT] = reverse[BOTTOM];
        reverse[BOTTOM] = reverse[RIGHT];
        reverse[RIGHT] = temp;

        List<Tile> tempList = null;
        if (edgeMap.containsKey(TOP)) {
            tempList = edgeMap.get(TOP);
            edgeMap.remove(TOP);
        }
        if (edgeMap.containsKey(LEFT)) {
            edgeMap.put(TOP, edgeMap.get(LEFT));
            edgeMap.remove(LEFT);
        }
        if (edgeMap.containsKey(BOTTOM)) {
            edgeMap.put(LEFT, edgeMap.get(BOTTOM));
            edgeMap.remove(BOTTOM);
        }
        if (edgeMap.containsKey(RIGHT)) {
            edgeMap.put(BOTTOM, edgeMap.get(RIGHT));
            edgeMap.remove(RIGHT);
        }
        if (tempList != null) {
            edgeMap.put(RIGHT, tempList);
        }
    }

    public void rotateCounterClockwise() {
        rotations -= 1;
        int temp = edge[TOP];
        edge[TOP] = edge[RIGHT];
        edge[RIGHT] = edge[BOTTOM];
        edge[BOTTOM] = edge[LEFT];
        edge[LEFT] = temp;
        temp = reverse[TOP];
        reverse[TOP] = reverse[RIGHT];
        reverse[RIGHT] = reverse[BOTTOM];
        reverse[BOTTOM] = reverse[LEFT];
        reverse[LEFT] = temp;
        List<Tile> tempList = edgeMap.get(TOP);
        edgeMap.put(TOP, edgeMap.get(RIGHT));
        edgeMap.put(RIGHT, edgeMap.get(BOTTOM));
        edgeMap.put(BOTTOM, edgeMap.get(LEFT));
        edgeMap.put(LEFT, tempList);
    }

    public void flipHorizontal() {
        horizontalFlip += 1;
        int temp = edge[RIGHT];
        edge[RIGHT] = edge[LEFT];
        edge[LEFT] = temp;
        temp = reverse[RIGHT];
        reverse[RIGHT] = reverse[LEFT];
        reverse[LEFT] = temp;
        temp = edge[TOP];
        edge[TOP] = reverse[TOP];
        reverse[TOP] = temp;
        temp = edge[BOTTOM];
        edge[BOTTOM] = reverse[BOTTOM];
        reverse[BOTTOM] = temp;
        List<Tile> tempList = null;
        if (edgeMap.containsKey(RIGHT)) {
            tempList = edgeMap.get(RIGHT);
            edgeMap.remove(RIGHT);
        }
        if (edgeMap.containsKey(LEFT)) {
            edgeMap.put(RIGHT, edgeMap.get(LEFT));
            edgeMap.remove(LEFT);
        }
        if (tempList != null) {
            edgeMap.put(LEFT, tempList);
        }
    }

    public void flipVertical() {
        verticalFlip += 1;
        int temp = edge[TOP];
        edge[TOP] = edge[BOTTOM];
        edge[BOTTOM] = temp;
        temp = reverse[TOP];
        reverse[TOP] = reverse[BOTTOM];
        reverse[BOTTOM] = temp;
        temp = edge[RIGHT];
        edge[RIGHT] = reverse[RIGHT];
        reverse[RIGHT] = temp;
        temp = edge[LEFT];
        edge[LEFT] = reverse[LEFT];
        reverse[LEFT] = temp;
        List<Tile> tempList = null;
        if (edgeMap.containsKey(TOP)) {
            tempList = edgeMap.get(TOP);
            edgeMap.remove(TOP);
        }
        if (edgeMap.containsKey(BOTTOM)) {
            edgeMap.put(TOP, edgeMap.get(BOTTOM));
            edgeMap.remove(BOTTOM);
        }
        if (tempList != null) {
            edgeMap.put(BOTTOM, tempList);
        }
    }

    public int[] getEdges() {
        return edge;
    }

    public int getNumber() {
        return tileNumber;
    }

    public long getMatches() {
        return edgeMap.values().size();
    }

    public int[] getReverse() {
        return reverse;
    }

    public boolean topLeft() {
        return edgeMap.containsKey(BOTTOM) && edgeMap.containsKey(RIGHT);
    }

    public void makeTopLeft() {
        if (edgeMap.containsKey(BOTTOM)) {
            rotateClockwise();
        } else {
            rotateCounterClockwise();
        }
    }

    public Tile bottomNeighbor() {
        assert edgeMap.containsKey(BOTTOM);

        Tile bottom = edgeMap.get(BOTTOM).get(0);

        while (!bottom.edgeMap.containsKey(TOP) || !bottom.edgeMap.get(TOP).contains(this)) {
           bottom.rotateClockwise();
        }
        if (edge[BOTTOM] != bottom.reverse[TOP]) {
            bottom.flipHorizontal();
        }
        assert edge[BOTTOM] == bottom.reverse[TOP];
        return bottom;
    }

    public Tile rightNeighbor() {
        assert edgeMap.containsKey(RIGHT);

        Tile right = edgeMap.get(RIGHT).get(0);

        while (!(right.edgeMap.containsKey(LEFT) && right.edgeMap.get(LEFT).contains(this))) {
            right.rotateClockwise();
        }
        if (edgeMap.containsKey(TOP) && right.edgeMap.containsKey(TOP)) {
            Tile top = edgeMap.get(TOP).get(0).edgeMap.get(RIGHT).get(0);
            if (!right.edgeMap.get(TOP).contains(top)) {
                right.flipVertical();
            }
        } else if ((!edgeMap.containsKey(TOP) && right.edgeMap.containsKey(TOP)) ||
                (edgeMap.containsKey(TOP) && !right.edgeMap.containsKey(TOP))) {
            right.flipVertical();
        }
        assert edge[RIGHT] == right.reverse[LEFT];
        return right;
    }

    public int getSize() {
        return imageData.size() - 2;
    }
}
