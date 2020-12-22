package com.e3civichigh.aoc.y2020.day17;

import java.util.ArrayList;

public class RuleSet<T extends Cell<T>> {
    private final ArrayList<Rule<T>> rules = new ArrayList<>();

    public RuleSet() {
        rules.add(new ActivateRule<>());
        rules.add(new SuspendRule<>());
    }

    public void apply(T cell) {
        for (Rule<T> rule : rules) {
            if (rule.applyRule(cell)) {
                break;
            }
        }
    }

}