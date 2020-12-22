package com.e3civichigh.aoc.y2020.day07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bag {
    private String color;
    private List<Bag> containedBy = new ArrayList<>();
    private Map<Bag, Integer> contains = new HashMap<>();


    private Bag(String color) {
        this.color = color;
        Bags.getInstance().put(color, this);
    }

    public static void addBag(String rule) {
        Pattern p = Pattern.compile("(.*)\\sbags contain(\\s(\\d+\\s.*|no other)\\sbag[s]*[\\,|\\.]?)");
        Matcher match = p.matcher(rule);
        if (match.matches()) {
            String color = match.group(1);
            Bag bag;
            if (Bags.getInstance().containsKey(color)) {
                bag = Bags.getInstance().get(color);
            } else {
                bag = new Bag(color);
            }
            for (String contains : match.group(2).split("(\\,|\\.)")) {
                if (contains.equals("no other bags")) continue;

                p = Pattern.compile("\\s(\\d+)\\s(.*)\\sbag.*");
                match = p.matcher(contains);
                if (match.matches()) {
                    Bag innerBag;
                    color = match.group(2);
                    if (Bags.getInstance().containsKey(color)) {
                        innerBag = Bags.getInstance().get(color);
                    } else {
                        innerBag = new Bag(color);
                    }
                    innerBag.containedBy.add(bag);
                    bag.contains.put(innerBag, Integer.valueOf(match.group(1)));
                }
            }
        }

    }

    public List<Bag> getContainedBy() {
        return containedBy;
    }

    public String getColor() {
        return color;
    }

    public Map<Bag, Integer> getContains() {
        return contains;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(color);
        result.append(" bags contain ");
        for (Bag bag : contains.keySet()) {
            result.append(contains.get(bag));
            result.append(String.format(" %s bags, ", bag.getColor()));
        }
        result.append("\n");
        return result.toString();
     }
}
