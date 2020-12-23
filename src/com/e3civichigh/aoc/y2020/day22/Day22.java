package com.e3civichigh.aoc.y2020.day22;

import com.e3civichigh.aoc.Day;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 extends Day {

    List<Deque<Integer>> hands = new ArrayList<>();
    private int game = 0;

    public static void main(String[] args) {
        Day22 day = new Day22();
        InputStream stream = day.getInputStream(args);
        day.readHands(stream);
        day.playGame();
        System.out.println(day.computeScore());
    }

    private long computeScore() {
        Deque<Integer> winningHand = hands.stream().filter(s -> s.size() > 0).findFirst().orElseThrow();

//        winningHand.forEach(System.out::println);

        int length = winningHand.size();
        return IntStream.range(0, length)
                .map(i -> (length - i))
                .map(n -> winningHand.pop() * n)
                .sum();
    }

    private void playGame() {
        try {
            recursiveCombat(false, hands, null);
        } catch (Exception e) {
            System.out.println("Game ended after a loop.");
        }
    }

    private int recursiveCombat(boolean copy, List<Deque<Integer>> inHands, List<Integer> previouslyPlayed) throws Exception {
        List<String> rounds = new ArrayList<>();
        System.out.format("\n=== Game %d ===\n", ++game);
        int localGame = game;

        List<Deque<Integer>> recursiveHands = new ArrayList<>();
        if (copy) {
            for (int i = 0; i < inHands.size(); i++) {
                recursiveHands.add(new LinkedList<>());
                Iterator<Integer> iterator = inHands.get(i).iterator();
                for (int j = 0; j < previouslyPlayed.get(i); j++) {
                    recursiveHands.get(i).add(iterator.next());
                }
            }
        } else {
            recursiveHands = inHands;
        }

        boolean done = false;
        int winner = 0;
        int round = 0;

        while (!done) {
            round += 1;

            if (round == 1) {
                System.out.format("\n-- Round %d (Game %d) --\n", ++round, localGame);
                for (int i = 0; i < recursiveHands.size(); i++) {
                    System.out.format("Player %d's deck: %s\n", i + 1, recursiveHands.get(i).toString());
                }
            }

            // End on infinite recursion
            String record = recursiveHands.stream().map(Object::toString).collect(Collectors.joining("|"));
            if (rounds.contains(record)) {
               throw new Exception("Player 1 Wins, infinite loop.");
            }
            rounds.add(record);

            List<Integer> playedCards = new ArrayList<>();

            for (Deque<Integer> hand: recursiveHands) {
                playedCards.add(hand.isEmpty() ? null: hand.pop());
            }
//            for (int i =0; i < playedCards.size(); i++) {
//                System.out.format("Player %d plays: %d\n", i + 1, playedCards.get(i));
//            }
            if (localGame == 25) throw new RuntimeException();

            // If both players have at least as many cards remaining in their deck as the value
            // of the card they just drew, the winner of the round is determined by playing a
            // new game of Recursive Combat.


            List<Deque<Integer>> finalRecursiveHands = recursiveHands;
            if (IntStream.range(0, playedCards.size()).filter(i -> playedCards.get(i) <= finalRecursiveHands.get(i).size()).count() == playedCards.size()) {
//                System.out.println("Playing a sub-game to determine the winner...");
                try {
                    winner = recursiveCombat(true, recursiveHands, playedCards);
                } catch (Exception e) {
                    winner = 0;
                }
            } else {

                winner = IntStream.range(0, playedCards.size())
                        .boxed().max(Comparator.comparing(playedCards::get))
                        .orElse(0);
            }

            int loser = winner == 0 ? 1 : 0;
            recursiveHands.get(winner).add(playedCards.get(winner));
            recursiveHands.get(winner).add(playedCards.get(loser));

//            System.out.format("Player %d wins round %d of game %d!\n", winner+1, round, localGame);

            done = recursiveHands.stream().filter(c -> c.size() > 0).count() < 2;
        }

        return winner;

    }

    private void readHands(InputStream stream) {
        try (Scanner input = new Scanner(stream)) {
            while (input.hasNextLine()) {
                // ignore player
                input.nextLine();
                Deque<Integer> playerHand = new LinkedList<>();
                hands.add(playerHand);

                String line = input.nextLine();
                while (!line.isEmpty()) {
                    playerHand.add(Integer.parseInt(line));
                    if (input.hasNextLine()) {
                        line = input.nextLine();
                    } else {
                        line = "";
                    }
                }

            }
        }
    }
}
