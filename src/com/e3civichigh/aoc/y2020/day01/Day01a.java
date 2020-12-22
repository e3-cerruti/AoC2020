package com.e3civichigh.aoc.y2020.day01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day01a {
    public static void main(String[] args) {
        List<Integer> numbers;
        try {
            URL url = Day01a.class.getResource("input.txt");
            if (url == null) {
                throw new FileNotFoundException();
            }

            numbers = Files.readAllLines(Paths.get(url.toURI()))
                    .stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        List<Integer> values = IntStream
            .range(0, numbers.size())
            .filter(i -> numbers.subList(i+1, numbers.size()).contains(2020 - numbers.get(i)))
            .mapToObj(numbers::get)
            .collect(Collectors.toList());

        if (values.size() == 1) {
            System.out.println(values.get(0) * (2020 - values.get(0)));
        } else {
            System.out.println("Something went wrong");
        }
    }
}
