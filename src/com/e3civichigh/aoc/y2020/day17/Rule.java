package com.e3civichigh.aoc.y2020.day17;

public interface Rule<T extends Cell<T>> {
    boolean applyRule(T cell);
}
