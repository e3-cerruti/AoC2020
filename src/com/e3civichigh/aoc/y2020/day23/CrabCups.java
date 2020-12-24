package com.e3civichigh.aoc.y2020.day23;

import java.util.ArrayList;
import java.util.List;

public class CrabCups {
    private static final int ROUNDS = 10_000_000;
//    private static final int ROUNDS = 100;
    private static final int NUMBER_OF_CUPS = 1_000_000;
//    private static final int NUMBER_OF_CUPS = 9;
    private static List<Integer> circleOfCups;

    public static void main(String[] args) {
        String cups = args.length >= 1 && args[0] != null ? args[0] : "389125467";
        initializeCups(cups);
        playGame();
    }

    public static void playGame() {
        Integer currentLabel = circleOfCups.get(0);

        for (int round = 0; round < ROUNDS; round++) {
            List<Integer> inHand = pickupCups(currentLabel);
            Integer destination = findDestination(currentLabel, inHand);
            placeCups(destination, inHand);
            currentLabel = next(currentLabel);
            if (round % 10_000 == 0) System.out.println(round);
        }

        int start = circleOfCups.indexOf(1);
        if (ROUNDS > 100) {
            // Part 2
            Integer one = circleOfCups.get(wrap(start + 1));
            Integer two = circleOfCups.get(wrap(start + 2));
            System.out.format("%d + %d = %d\n", one, two, (long) one * (long) two);
        } else {
            // Part 1
            List<Integer> result = circleOfCups.subList(start + 1, circleOfCups.size());
            result.addAll(circleOfCups.subList(0, start));

            StringBuilder answer = new StringBuilder();
            for (Integer cup: result) {
                answer.append(cup);
            }
            System.out.println(answer.toString());
        }
    }

    private static List<Integer> pickupCups(Integer currentLabel) {
        int currentCup = circleOfCups.indexOf(currentLabel);
        List<Integer> inHand = new ArrayList<>(3);

        for (int i = currentCup + 1; i <= currentCup +3; i++) {
            inHand.add(circleOfCups.get(wrap(i)));
        }
        circleOfCups.removeAll(inHand);
        return inHand;
    }

    private static Integer findDestination(int currentLabel, List<Integer> inHand) {
        int destinationLabel = previous(currentLabel);
        while (inHand.contains(destinationLabel)) {
            destinationLabel = previous(destinationLabel);
        }

        return circleOfCups.indexOf(destinationLabel) + 1;
    }

    private static Integer previous(Integer current) {
        return current == 1 ? NUMBER_OF_CUPS : current - 1;
    }

    private static Integer next(Integer current) {
        int index = wrap(circleOfCups.indexOf(current) + 1);
        return circleOfCups.get(index);
    }

    private static void placeCups(Integer destination, List<Integer> inHand) {
        circleOfCups.addAll(destination, inHand);
    }

    public static void initializeCups(String cups) {
        circleOfCups = new ArrayList<>(cups.length());
        for (int i = 0; i < cups.length(); i++) {
            circleOfCups.add(Integer.parseInt(cups.substring(i, i+1)));
        }
        // circle.addAll(cups.chars().mapToObj(Character::getNumericValue).collect(Collectors.toList()));
        int remainderStart = highestLabel(circleOfCups) + 1;

        for (int cup = remainderStart; cup <= NUMBER_OF_CUPS; cup++) {
            circleOfCups.add(cup);
        }
        // circle.addAll(IntStream.rangeClosed(remainderStart, MINIMUM_CUPS).boxed().collect(Collectors.toList()));
    }

    private static int highestLabel(List<Integer> circle) {
        int max = Integer.MIN_VALUE;
        for (Integer cup: circle) {
            max = (cup > max ? cup : max);
        }
        return max;
    }

    public static int wrap(int i) {
        return (i + circleOfCups.size()) % circleOfCups.size();
    }
}
