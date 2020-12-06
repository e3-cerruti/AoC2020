package day06;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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
         * By adding the individual answers to a list if theyare not present
         * we find the union of all the questions that were answered yes.
         * The sum of the sizes of these lists is the result.
         */
        int result = 0;
        for (String group : groups) {
            List<String> answers = new ArrayList<>();
            String[] members = group.split("\n");
            for (String member : members) {
                String[] questions = member.split("");
                for (String question : questions) {
                    if (!answers.contains(question)) {
                        answers.add(question);
                    }
                }
            }
            result += answers.size();
        }

        System.out.println(result);

        /*
         * Part 2: Any question where everyone in the group answered yes
         *
         * Start with the entire alphabet. Then intersect that set with the answer
         * from each member. The final list will contain the questions that every
         * member answered yes. The answer is the sum of the length of all
         * the final lists.
         */
        result = 0;
        for (String group : groups) {
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            List<String> answers = new ArrayList<>();

            for (int i = 0; i < alphabet.length(); i++) {
                answers.add(alphabet.substring(i, i+1));
            }

            String[] members = group.split("\n");

            for (String member : members) {
                List<String> delete = new ArrayList<>();
                for (int i = 0; i < answers.size(); i++) {
                    String item = answers.get(i);
                    if (!member.contains(item)) {
                        delete.add(item);
                    }
                }
                answers.removeAll(delete);
            }
            result += answers.size();
        }

        System.out.println(result);

    }
}
