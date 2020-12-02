package day01;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day01b {
    public static void main(String[] args) {
        List<Integer> numbers;
        try {
            URL url = Day01b.class.getResource("input.txt");
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

        //Optional<Integer> result = numbers.stream().filter(n -> numbers.contains(2020 - n)).findFirst();
        for (int i1 = 0; i1 < numbers.size() ; i1++) {
            Integer n1 = numbers.get(i1);
            if (n1 > 2020) continue;
            for (int i2 = i1 + 1; i2 < numbers.size(); i2++) {
                Integer n2 = numbers.get(i2);
                if (n1 + n2 > 2020) continue;
                Optional<Integer> n3 = numbers.stream().skip(n2).filter(n -> n == (2020 - (n1 + n2))).findFirst();

                if (n3.isPresent()) {
                    System.out.println(n1 * n2 * n3.get());
                    return;
                }
            }
        }

        System.out.println("Something went wrong");
    }
}
