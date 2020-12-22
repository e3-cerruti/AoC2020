package com.e3civichigh.aoc.y2020.day13;

class Shuttle {
    private final int number;
    private final int order;
    private final boolean valid;

    public Shuttle(String name, int order) {
        this.order = order;
        this.valid = !name.equals("x");
        number = valid ? Integer.parseInt(name) : -1;
    }

    public long firstDepartureAfter(long t) {
        assert valid;
        return (number - t % number) % number;
    }

    public boolean departsInOrderAt(long t) {
        assert valid;
        return (t + order) % number == 0;
    }

    public int getNumber() {
        return number;
    }

    public boolean isValid() {
        return valid;
    }

    public long getInitialDeparture() {
        return number - order;
    }
}
