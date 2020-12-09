package day09;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 {
    public static final int PATTERN_LENGTH = 25;
    public static void main(String[] args) {
        List<BigInteger> numbers;
        try {
            URL url = Day09.class.getResource("input.txt");
            if (url == null) {
                throw new FileNotFoundException();
            }

            numbers = Files.readAllLines(Paths.get(url.toURI()))
                    .stream()
                    .map(BigInteger::new)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        BigInteger result = part1(numbers);
        System.out.println(result);

        BigInteger total = part2(numbers, result);
        System.out.println(total);
    }

    private static BigInteger part1(List<BigInteger> numbers) {
        BigInteger result = null;

        for (int i = 1; i < numbers.size() - PATTERN_LENGTH; i++) {
            boolean found = false;
            BigInteger target = numbers.get(PATTERN_LENGTH + i);
            for (int j = i; j < i + PATTERN_LENGTH; j++) {
                if (numbers.subList(j + 1, i + PATTERN_LENGTH).contains(target.subtract(numbers.get(j)))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result = target;
                break;
            }
        }
        return result;
    }

    private static BigInteger part2(List<BigInteger> numbers, BigInteger search) {
        int start = 0;
        int end = 1;
        BigInteger total = sumRange(numbers, start, end);

        while (!total.equals(search)) {
            end += 1;
            if (total.compareTo(search) > 0 || end > numbers.size()) {
                start += 1;
                end = start + 1;
            }
            total = sumRange(numbers, start, end);
        }

        return Collections.min(numbers.subList(start, end)).add(Collections.max(numbers.subList(start, end)));
    }

    private static BigInteger sumRange(List<BigInteger> numbers, int start, int end) {
        BigInteger sum = BigInteger.valueOf(0);
        for (BigInteger number: numbers.subList(start, end)) {
            sum = sum.add(number);
        }

        return sum;
    }

}
