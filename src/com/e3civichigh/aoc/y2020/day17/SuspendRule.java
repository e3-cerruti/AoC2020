package com.e3civichigh.aoc.y2020.day17;

public class SuspendRule<T extends Cell<T>> implements Rule<T> {
    @Override
    public boolean applyRule(T cell) {
        if (!cell.isActive()) return false;

        long activeNeighborCount = cell.getActiveNeighborCount();
        if (activeNeighborCount != 2 && activeNeighborCount != 3) {
            cell.setFutureSuspended();
            return true;
        }
        return false;
    }

}
