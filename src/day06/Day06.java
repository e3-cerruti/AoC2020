package day06;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day06 {
    public static void main(String[] args) {
        URL url = Day06.class.getResource("input.txt");
        String forms = "";
        try {
            if (url == null) {
                    throw new FileNotFoundException();
            }

            forms = Files.readString(Paths.get(url.toURI()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        String[] groups = forms.split("\n\n");

        /*
         * Part 1: Every question where anyone in the group answered yes
         *
         * By adding the individual answers to a set we find the union of
         * all the questions that were answered yes. The sum of the sizes
         * of these set is the result.
         */
        int result = 0;
        for (String group : groups) {
            Set<Character> answers = new HashSet<>();
            String[] members = group.split("\n");
            for (String member : members) {
                answers.addAll(member.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
            }
            result += answers.size();
        }

        System.out.println(result);

        /*
         * Part 2: Any question where everyone in the group answered yes
         *
         * Start with the entire set. Then intersect that set with the answer
         * from each member. The final set will list the questions that every
         * member answered yes. The answer is the sum of the length of all
         * the groups final sets.
         */
        result = 0;
        for (String group : groups) {
            Set<Character> answers = "abcdefghijklmnopqrstuvwxyz"
                    .chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toSet());
            String[] members = group.split("\n");

            for (String member : members) {
                answers = member
                        .chars()
                        .mapToObj(c -> (char) c)
                        .filter(answers::contains)
                        .collect(Collectors.toSet());
            }
            result += answers.size();
        }

        System.out.println(result);

    }
}
