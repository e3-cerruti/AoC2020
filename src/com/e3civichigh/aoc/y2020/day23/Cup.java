package com.e3civichigh.aoc.y2020.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cup {
    public static final int NUMBER_OF_CUPS_TO_PICKUP = 3;
    private final Integer label;
    private Cup nextCup;
    private static Cup lastCup = null;
    private static Integer lowestCupLabel = Integer.MAX_VALUE;
    private static Integer highestCupLabel = Integer.MIN_VALUE;


    // 1. The crab picks up the three cups that are immediately clockwise of the current cup. They are removed
    //    from the circle; cup spacing is adjusted as necessary to maintain the circle.

    // 2. The crab selects a destination cup: the cup with a label equal to the current cup's label minus one.
    //    If this would select one of the cups that was just picked up, the crab will keep subtracting one until
    //    it finds a cup that wasn't just picked up. If at any point in this process the value goes below the
    //    lowest value on any cup's label, it wraps around to the highest value on any cup's label instead.

    // 3. The crab places the cups it just picked up so that they are immediately clockwise of the destination cup.
    //    They keep the same order as when they were picked up.

    // 4. The crab selects a new current cup: the cup which is immediately clockwise of the current cup.

    public Cup(Integer label) {
        this.label = label;
        lowestCupLabel = Math.min(lowestCupLabel, label);
        highestCupLabel = Math.max(highestCupLabel, label);
        if (lastCup != null) lastCup.nextCup = this;
        lastCup = this;
    }

    public static void printCups(Cup start, Cup currentCup) {
        Cup nextCup = start;
        do {
            printCup(nextCup, currentCup);
            nextCup = nextCup.nextCup;
        } while (nextCup != null && !nextCup.equals(start));
        System.out.println();
    }

    public static void printCup(Cup cup, Cup currentCup) {
        System.out.format(cup.equals(currentCup)?"(%d) ":"%d ", cup.label);
    }

    public static void setLastCupNext(Cup currentCup) {
        lastCup.setNextCup(currentCup);
    }

    public static String result(Map<Integer, Cup> circle) {
        StringBuilder sequence = new StringBuilder();

        Cup cup = circle.get(1);
        while (cup.nextCup != circle.get(1)) {
            cup = cup.nextCup;
            sequence.append(cup.label);
        }
        return sequence.toString();
    }

    public static int getHighestLabel() {
        return highestCupLabel;
    }

    public void setNextCup(Cup cup) {
        this.nextCup = cup;
    }

    public Cup pickupCups() {
        Cup cup = this.nextCup;

        Cup finalCup = this;
        for (int i = 0; i < NUMBER_OF_CUPS_TO_PICKUP; i++) {
            finalCup = finalCup.nextCup;
        }

        this.nextCup = finalCup.nextCup;
        finalCup.nextCup = null;
        return cup;
    }

    public Cup findDestination(Map<Integer, Cup> circle, Cup inHand) {
        Integer destinationLabel = label - 1;
        if (destinationLabel < lowestCupLabel) {
            destinationLabel = highestCupLabel;
        }
        List<Integer> inHandLabels = new ArrayList<>();

        Cup inHandCup = inHand;
        do {
            inHandLabels.add(inHandCup.label);
            inHandCup = inHandCup.next();
        } while (inHandCup != null);

        while (inHandLabels.contains(destinationLabel) ) {
            destinationLabel -= 1;
            if (destinationLabel < lowestCupLabel) {
                destinationLabel = highestCupLabel;
            }
        }

        return circle.get(destinationLabel);
    }

    public Integer getLabel() {
        return label;
    }

    public static void placeCups(Cup destination, Cup inHand) {
        Cup nextCup = destination.nextCup;
        destination.nextCup = inHand;
        Cup currentCup = inHand;
        while (currentCup.nextCup != null) {
            currentCup = currentCup.nextCup;
        }
        currentCup.nextCup = nextCup;
    }

    public void printDestination() {
        System.out.format("Destination: %d\n", label);
    }

    public Cup next() {
        return nextCup;
    }
}
