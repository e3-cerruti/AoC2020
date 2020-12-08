package day07;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day07 {
    public static void main(String[] args) {
        URL url = Day07.class.getResource("input.txt");
        List<String> rules = null;
        try {
            if (url == null) {
                throw new FileNotFoundException();
            }

            rules = Files.readAllLines(Paths.get(url.toURI()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        List<Bag> bags = new ArrayList<>();
        for (String rule: rules) {
            Bag.addBag(rule);
        }

        Bag shinyGold = Bags.getInstance().get("shiny gold");
        List<Bag> seen =  new ArrayList<Bag>();
        printContainer(shinyGold, seen);
        System.out.println(seen.size());

        int count = countBags(shinyGold);
        System.out.println(count-1);

    }

    private static int countBags(Bag parent) {
        int count = 0;
        Map<Bag, Integer> contains = parent.getContains();
        for (Bag bag : contains.keySet()) {
            count += (countBags(bag) * contains.get(bag));
        }
        return count + 1;
    }

    private static void printContainer(Bag parent, List<Bag> seen) {
        for (Bag bag : parent.getContainedBy()) {
            if (!seen.contains(bag)) {
                seen.add(bag);
                printContainer(bag, seen);
            }
        }
    }
}
