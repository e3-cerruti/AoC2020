package com.e3civichigh.aoc.y2020.day23;

import com.e3civichigh.aoc.Day;

import java.util.HashMap;
import java.util.Map;

public class Day23 extends Day {
    private static final int ROUNDS = 10000000;

    public static void main(String[] args) {
        String cups = args.length >= 1 && args[0] != null ? args[0] : "389125467";
        Map<Integer, Cup> circle = new HashMap<>(cups.length());
        cups.chars().mapToObj(Character::getNumericValue).forEach(c -> circle.put(c, new Cup(c)));

        for (int cup = Cup.getHighestLabel()+1; cup <= 1000000; cup++) {
            circle.put(cup, new Cup(cup));
        }

        Cup currentCup = circle.get(Character.getNumericValue(cups.charAt(0)));
        Cup.setLastCupNext(currentCup);

        // Play Game
        for (int round = 0; round < ROUNDS; round++) {
//            System.out.format("-- move %d --\n", round+1);
//            Cup start = circle.get(circle.keySet().iterator().next());
//            Cup.printCups(start, currentCup);
            Cup inHand = currentCup.pickupCups();
//            System.out.print("Pick up: ");
//            Cup.printCups(inHand, currentCup);
            Cup destination = currentCup.findDestination(circle, inHand);
//            destination.printDestination();
            Cup.placeCups(destination, inHand);
            currentCup = currentCup.next();
        }
        System.out.format("-- final --\n");
        Cup start = circle.get(1);
        Cup one = start.next();
        Cup two = one.next();
        System.out.println(one.getLabel() + " " + two.getLabel() + " = " + (long) one.getLabel() * (long) two.getLabel());

    }
}
