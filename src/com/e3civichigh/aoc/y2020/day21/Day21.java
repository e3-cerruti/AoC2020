package com.e3civichigh.aoc.y2020.day21;

import com.e3civichigh.aoc.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {
    private static List<String> input;
    private Map<String, Set<String>> allergenMap;

    public static void main(String[] args) {
        Day21 day = new Day21();
        try {
            input = day.getInputList(args);
        } catch (AdventOfCodeException e) {
            System.err.println("Unable to read input.");
            e.printStackTrace();
        }

        System.out.println("Part 1:" + day.runPart1());
        System.out.println("Part 2:" + day.runPart2());
    }

    @Override
    public long runPart1() {
        allergenMap = new HashMap<>(input.size());

        List<String> ingredientList = new ArrayList<>(input.size());

        for (String line : input) {
            String[] parts = line.split("[(|)]");

            Set<String> ingredientsForProduct = new HashSet<>(Arrays.asList(parts[0].split(" ")));
            ingredientList.addAll(ingredientsForProduct);

            Set<String> allergensForProduct = new HashSet<>(Arrays.asList(parts[1].substring(9).split(", ")));

            for (String allergen : allergensForProduct) {
                if (allergenMap.containsKey(allergen)) {
                    Set<String> ingredients = new HashSet<>();
                    for (String existingIngredient : allergenMap.get(allergen)) {
                        if (ingredientsForProduct.contains(existingIngredient)) {
                            ingredients.add(existingIngredient);
                        }
                    }
                    allergenMap.put(allergen, ingredients);
                } else {
                    allergenMap.put(allergen, ingredientsForProduct);
                }
            }

        }

        HashSet<String> finalSet = new HashSet<>();
        allergenMap.forEach((a, s) -> finalSet.addAll(s));
        finalSet.forEach(System.out::println);

        return ingredientList.stream().filter(i -> !finalSet.contains(i)).count();
    }

    @Override
    public long runPart2() {
        Set<String> solo = new HashSet<>();
        boolean done = false;
        while (!done) {
            done = true;
            for (Set<String> ingredients : allergenMap.values()) {
                if (ingredients.size() == 1) {
                    solo.add(String.valueOf(ingredients.toArray()[0]));
                } else {
                    ingredients.removeAll(solo);
                    done = false;
                }
            }
        }

        List<String> result = new ArrayList<>();
        List<String> keys = new ArrayList<>(allergenMap.keySet());
        Collections.sort(keys);
        List<String> answer = new ArrayList<>();
        for (String allergen : keys) {
            System.out.println(allergen + ": " + allergenMap.get(allergen));
            answer.add((String) allergenMap.get(allergen).toArray()[0]);
        }

        System.out.println(String.join("," , answer));
        return 0L;
    }
}