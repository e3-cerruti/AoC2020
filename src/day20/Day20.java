package day20;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 {
    private final InputStream dataFile;
    private final List<Tile> tiles = new ArrayList<>();
    private final List<Tile> corners = new ArrayList<>();
    private int sideLength;

    public Day20(String fileName) {
        dataFile = Day20.class.getResourceAsStream(fileName);
    }


    public static void main(String[] args) {
        Day20 day = new Day20(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.processFile();
        day.buildEdgeMap();
        Tile[][] tileMap = day.buildTileMap();
        char[][] image = day.getImageFromTileMap(tileMap);

        Map<Integer, List<int[]>> monsters =  day.findMonsters(image);
        List<int[]> locations = new ArrayList<>();
        int imageNumber = -1;
        for (int monster: monsters.keySet()) {
            if (monsters.get(monster).size() > locations.size()) {
                locations = monsters.get(monster);
                imageNumber = monster;
            }
        }

        char[][] editedImage = day.editImage(imageNumber, locations, image);

        int waves = 0;
        for (char[] line: image) {
            System.out.println(new String(line));
            for (char c : line) {
                waves += c == '#' ? 1 : 0;
            }
        }

        System.out.println(waves);
    }

    private char[][] editImage(int monster, List<int[]> locations, char[][] image) {
        assert monster > 0;

        for (int i = 0; i < monster; i++) rotate90Clockwise(image);

        for (int[] location : locations) {
            image[location[0] - 1][location[1] + 18] = 'O';
            for (int x : new int[]{0,5,6,11,12,17,18,19}) image[location[0]][location[1]+x] = 'O';
            for (int x : new int[]{1,4,7,10,13,16}) image[location[0] + 1][location[1]+x] = 'O';
        }

        return image;

    }

    private Map<Integer, List<int[]>> findMonsters(char[][] image) {
        Map<Integer, List<int[]>> monstersFound = new HashMap<>();

        String monster2 =  "#....##....##....###";
        String monster3 =  ".#..#..#..#..#..#";

        Pattern m2 = Pattern.compile(monster2);
        Pattern m3 = Pattern.compile(monster3);

        String eyeString = null;
        ArrayList<Integer> humps = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int lineNumber = 0;
            for (char[] line : image) {
                String test = new String(line);
                System.out.print(line);

                Matcher finalMatcher = m3.matcher(test);
                int startAt = humps.isEmpty() ? 0 : humps.get(0);
                while (!humps.isEmpty() && finalMatcher.find(startAt)) {
                    int pos = finalMatcher.start();
                    System.out.print("\tBody found at " + pos);
                    if (humps.contains(pos)) {
                        int[] location = new int[]{lineNumber - 1, pos};
                        monstersFound.computeIfAbsent(i, k -> new ArrayList<int[]>()).add(location);
                        System.out.print("\tFound monster in " + i);
                    }
                    humps.remove(0);
                    startAt = humps.isEmpty() ? pos + 1 : humps.get(0);
                }

                humps.clear();
                Matcher m = m2.matcher(test);
                int humpsPosition = 0;
                while (m.find(humpsPosition)) {
                    int pos = m.start();
                    System.out.print("\tHumps found at " + pos);
                    if (eyeString != null && eyeString.charAt(pos + 18) == '#') {
                        humps.add(pos);
                        System.out.print("\tHumps added " + pos);
                    }
                    humpsPosition = pos + 1;
                }
                eyeString = test;
                System.out.println();
                lineNumber += 1;
            }
            rotate90Clockwise(image);
            System.out.println("\n\n\n");
        }

//        reverseArray(image);
//
//        for (int i = 0; i < 4; i++) {
//            for (char[] line : image) {
//                String test = new String(line);
//                System.out.print(line);
//
//                Matcher finalMatcher = m3.matcher(test);
//                int startAt = humps.isEmpty() ? 0 : humps.get(0);
//                while (!humps.isEmpty() && finalMatcher.find(startAt)) {
//                    int pos = finalMatcher.start();
//                    System.out.print("\tBody found at " + pos);
//                    if (humps.contains(pos)) {
//                        monstersFound += 1;
//                        System.out.print("\tFound monster in " + i);
//                    }
//                    humps.remove(0);
//                    startAt = humps.isEmpty() ? pos + 1 : humps.get(0);;
//                }
//
//                humps.clear();
//                Matcher m = m2.matcher(test);
//                int humpsPosition = 0;
//                while (m.find(humpsPosition)) {
//                    int pos = m.start();
//                    System.out.print("\tHumps found at " + pos);
//                    if (eyeString != null && eyeString.charAt(pos + 18) == '#') {
//                        humps.add(pos);
//                        System.out.print("\tHumps added " + pos);
//                    }
//                    humpsPosition = pos + 1;
//                }
//                eyeString = test;
//                System.out.println();            }
//            rotate90Clockwise(image);
//            System.out.println("\n\n\n");
//        }

        return monstersFound;
    }

    @SuppressWarnings("unused")
    static void reverseArray(char[][] arr)
    {

        // Traverse each row of arr[][]
        for (int i = 0; i < arr.length; i++) {

            // Initialise start and end index
            int start = 0;
            int end = arr.length - 1;

            // Till start < end, swap the element
            // at start and end index
            while (start < end) {

                // Swap the element
                char temp = arr[i][start];
                arr[i][start] = arr[i][end];
                arr[i][end] = temp;

                // Increment start and decrement
                // end for next pair of swapping
                start++;
                end--;
            }
        }
    }

    static void rotate90Clockwise(char[][] a)
    {
        int N = a.length;

        // Traverse each cycle
        for (int i = 0; i < N / 2; i++)
        {
            for (int j = i; j < N - i - 1; j++)
            {

                // Swap elements of each cycle
                // in clockwise direction
                char temp = a[i][j];
                a[i][j] = a[N - 1 - j][i];
                a[N - 1 - j][i] = a[N - 1 - i][N - 1 - j];
                a[N - 1 - i][N - 1 - j] = a[j][N - 1 - i];
                a[j][N - 1 - i] = temp;
            }
        }
    }

    private char[][] getImageFromTileMap(Tile[][] tileMap) {
        int tileWidth = tileMap[0][0].getSize();
        char[][] image = new char[tileWidth*sideLength][tileWidth*sideLength];

        for (int row = 0; row < sideLength; row++) {
            for (int column = 0; column < sideLength; column++) {
                char[][] tile = tileMap[column][row].getImageData();
                for (int imageLine = 0; imageLine < tile.length; imageLine++) {
                    if (tileWidth >= 0)
                        System.arraycopy(tile[imageLine], 0, image[row * tile.length + imageLine], column * tileWidth, tileWidth);
                }
            }
        }

        return image;
    }

    private void buildEdgeMap() {
        for (Tile tile: tiles) {
            System.out.print(String.format("%d:\t%10s\t%10s\t%10s\t%10s\n",
                    tile.getNumber(),
                    Integer.toBinaryString(tile.getEdges()[0]),
                    Integer.toBinaryString(tile.getEdges()[1]),
                    Integer.toBinaryString(tile.getEdges()[2]),
                    Integer.toBinaryString(tile.getEdges()[3])).replace(' ', '0'));
            System.out.print(String.format("%d:\t%10s\t%10s\t%10s\t%10s\n",
                    tile.getNumber(),
                    Integer.toBinaryString(tile.getReverse()[0]),
                    Integer.toBinaryString(tile.getReverse()[1]),
                    Integer.toBinaryString(tile.getReverse()[2]),
                    Integer.toBinaryString(tile.getReverse()[3])).replace(' ', '0'));
        }

        for (int i = 0; i < tiles.size() - 1 ; i++) {
            for (int j = i+1; j < tiles.size(); j++) {
                Tile other = tiles.get(j);
                tiles.get(i).matchEdge(other);
            }
        }

        long product = 1;
        for (Tile tile : tiles) {
            System.out.format("%d: %d edges\n", tile.getNumber(), tile.getMatches());
            if (tile.getMatches() == 2) {
                product *= tile.getNumber();
                corners.add(tile);
            }
        }

        System.out.println(product);
    }

    public Tile[][] buildTileMap() {
        Tile[][] tileMap = new Tile[sideLength][sideLength];


        // Build top row
        tileMap[0][0] = topLeftCorner();
        System.out.print(tileMap[0][0].getNumber()+" ");
        for (int row = 0; row < sideLength; row++) {
            if (row != 0) {
                tileMap[0][row] = tileMap[0][row - 1].bottomNeighbor();
                System.out.print(tileMap[0][row].getNumber()+" ");
            }

            for (int column = 1; column < sideLength; column++) {
                tileMap[column][row] = tileMap[column - 1][row].rightNeighbor();
                System.out.print(tileMap[column][row].getNumber()+" ");
            }
            System.out.println();
        }

        return tileMap;
    }

    private Tile topLeftCorner() {
        Tile cornerTile = null;
        for (Tile tile : corners) {
            if (tile.topLeft()) {
                cornerTile = tile;
                break;
            }
        }
        if (cornerTile == null) {
            cornerTile = corners.get(0);
            cornerTile.makeTopLeft();
        }
        corners.remove(cornerTile);
        return cornerTile;
    }

    public void processFile() {
        try (Scanner input = new Scanner(dataFile)) {
            while (input.hasNextLine()) {
                String tileNumber = input.nextLine();

                List<String> tileRows = new ArrayList<>();

                String row = input.nextLine();
                tileRows.add(row);

                for (int i = 1; i < row.length(); i++) {
                    tileRows.add(input.nextLine());
                }

                if (input.hasNextLine()) input.nextLine();
                tiles.add(new Tile(tileNumber, tileRows));
            }
        }
        sideLength = (int) Math.sqrt(tiles.size());
    }
}
