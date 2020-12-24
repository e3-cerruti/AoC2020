package com.e3civichigh.aoc.y2020.day24;

import com.e3civichigh.aoc.Day;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day24 extends Day {

    public static final Color COLOURCELL = Color.WHITE;
    public static final Color COLOURGRID = Color.BLUE;
    final static int EMPTY = 0;
    final static int BSIZE = 180; //board size.
    final static int BOFFSET = BSIZE / 2;
    final static int HEXSIZE = 4;    //hex size in pixels
    final static int BORDERS = 5;
    final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3; //screen size (vertical dimension).
    private static InputStream stream;
    int[][] board = new int[BSIZE][BSIZE];
    private Map<Integer, Map<Integer, Boolean>> hexGridCopy;
    private final Map<Integer, Map<Integer, Boolean>> hexGrid = new HashMap<>();
    private Set<String> newNeighbors;
    private int day = 0;
    private DrawingPanel panel;

    public static void main(String[] args) {
        Day24 day = new Day24();
        stream = day.getInputStream(args);
        System.out.println(day.runPart1());
        SwingUtilities.invokeLater(day::runPart2);
    }

    @Override
    public long runPart1() {

        Pattern pattern = Pattern.compile("(e|se|sw|w|nw|ne)");
        try (Scanner input = new Scanner(stream)) {
            while (input.hasNextLine()) {
                int[] location = new int[]{0, 0};

                String line = input.nextLine();
                Matcher m = pattern.matcher(line);
                while (m.find()) {
                    Neighbor n = Neighbor.getDirectionForMove(m.group());
                    if (n != null) {
                        location = n.getNeighbor(location);
                    } else {
                        throw new RuntimeException("Invalid input");
                    }
                }
                flipTileAt(location);
            }
        }
        return totalBlackTiles();
    }

    private void flipTileAt(int[] location) {
        int x = location[0];
        int y = location[1];
        hexGrid.computeIfAbsent(x, v -> new HashMap<>()).compute(y, (k, v) -> v == null || !v);
    }

    private long totalBlackTiles() {
        long total = 0;
        for (Integer x : hexGrid.keySet()) {
            for (Integer y : hexGrid.get(x).keySet()) {
                if (hexGrid.get(x).get(y)) {
                    total += 1;
                }
            }
        }
        return total;
    }

    void initGame() {
        hexmech.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.

        hexmech.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
        hexmech.setBorders(BORDERS);

        updateBoard();
    }

    private void updateBoard() {
        for (int[] ints : board) {
            Arrays.fill(ints, EMPTY);
        }
        for (Integer x : hexGrid.keySet()) {
            for (Integer y : hexGrid.get(x).keySet()) {
                if (hexGrid.get(x).get(y)) {
                    board[x + BOFFSET][y + BOFFSET] = 1;
                }
            }
        }
        board[BOFFSET][BOFFSET] = board[BOFFSET][BOFFSET] == 1 ? 'B' : (-1 * 'W');
    }

    private void createAndShowGUI() {
        panel = new DrawingPanel(board);

        JPanel buttonPanel = new JPanel();
        JButton next = new JButton("Next");
        next.addActionListener(e -> {
            nextDay();
            System.out.format("Total after day %d: %d\n", day, totalBlackTiles());
        });
        buttonPanel.add(next);
        JButton next10 = new JButton("Next 10");
        next10.addActionListener(e -> {
            for (int i = 0; i < 10; i++) {
                nextDay();
            }
            System.out.format("Total after day %d: %d\n", day, totalBlackTiles());
        });
        buttonPanel.add(next10);
        JButton next100 = new JButton("Next 100");
        next100.addActionListener(e -> {
            for (int i = 0; i < 100; i++) {
                nextDay();
            }
            System.out.format("Total after day %d: %d\n", day, totalBlackTiles());
        });
        buttonPanel.add(next100);

        //JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Hex Testing 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();
        content.add(panel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.PAGE_END);
        //this.add(panel);  -- cannot be done in a static context
        //for hexes in the FLAT orientation, the height of a 10x10 grid is 1.1764 * the width. (from h / (s+t))
        frame.setSize((int) (SCRSIZE / 1.23), SCRSIZE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public long runPart2() {
        initGame();
        createAndShowGUI();
        newNeighbors = new HashSet<>();

        return totalBlackTiles();
    }

    private Map<Integer, Map<Integer, Boolean>> deepCopy(Map<Integer, Map<Integer, Boolean>> grid) {
        Map<Integer, Map<Integer, Boolean>> copy = new HashMap<>();
        for (Integer x : grid.keySet()) {
            copy.put(x, new HashMap<>());
            for (Integer y : grid.get(x).keySet()) {
                copy.get(x).put(y, grid.get(x).get(y));
            }
        }
        return copy;
    }

    public void nextDay() {
        day += 1;
        hexGridCopy = deepCopy(hexGrid);
        newNeighbors.clear();

        // First process existing tiles, keep a list of tiles neighboring
        // black tiles that don't exist
        for (Integer x : hexGridCopy.keySet()) {
            for (Integer y : hexGridCopy.get(x).keySet()) {
                int n = getBlackNeighbors(x, y);
                if (hexGridCopy.get(x).get(y)) {
                    // Any black tile with zero or more than 2 black
                    // tiles immediately adjacent to it is flipped to white.
                    if (n == 0 || n > 2) {
                        hexGrid.get(x).put(y, false);
                    }
                } else {
                    // Any white tile with exactly 2 black tiles
                    // immediately adjacent to it is flipped to black.
                    if (n == 2) {
                        hexGrid.get(x).put(y, true);
                    }
                }
            }
        }

        for (String neighborString : newNeighbors) {
            String[] neighbor = neighborString
                    .replaceAll("\\[", "")
                    .replaceAll("]", "")
                    .split(", ");
            int x = Integer.parseInt(neighbor[0]);
            int y = Integer.parseInt(neighbor[1]);
            int n = getBlackNeighbors(x, y, false);
            if (n == 2) {
                hexGrid.computeIfAbsent(x, v -> new HashMap<>()).put(y, true);
            }
        }

        updateBoard();
        panel.repaint();
    }

    private int getBlackNeighbors(Integer x, Integer y) {
        return getBlackNeighbors(x, y, true);
    }

    private int getBlackNeighbors(Integer x, Integer y, boolean addNew) {
        int blackNeighbors = 0;
        for (int[] neighbor : Neighbor.neighborsFor(new int[]{x, y})) {
            int nx = neighbor[0];
            int ny = neighbor[1];
            if (hexGridCopy.containsKey(nx) && hexGridCopy.get(nx).containsKey(ny)) {
                if (hexGridCopy.get(nx).get(ny)) {
                    blackNeighbors += 1;
                }
            } else if (addNew) {
                newNeighbors.add(Arrays.toString(new int[]{nx, ny}));
            }
        }
        return blackNeighbors;
    }

    private enum Neighbor {
        E("e", 1, 1, 0),
        W("w", -1, -1, 0),
        SE("se", 0, 1, -1),
        SW("sw", -1, 0, -1),
        NE("ne", 0, 1, 1),
        NW("nw", -1, 0, 1);

        private final String move;
        private final int[] x;
        private final int y;


        Neighbor(String move, int evenX, int oddX, int y) {
            this.move = move;
            this.x = new int[]{evenX, oddX};
            this.y = y;
        }

        public static List<int[]> neighborsFor(int[] location) {
            List<int[]> neighbors = new ArrayList<>(6);

            for (Neighbor n : Neighbor.values()) {
                neighbors.add(n.getNeighbor(location));
            }

            return neighbors;
        }

        public static Neighbor getDirectionForMove(String move) {
            for (Neighbor n : Neighbor.values()) {
                if (n.move.equals(move)) return n;
            }
            return null;
        }

        private int[] getNeighbor(int[] location) {
            int x = location[0];
            int y = location[1];
            return new int[]{x + this.x[Math.abs(y) % 2], y + this.y};
        }
    }
}
