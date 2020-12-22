package com.e3civichigh.aoc.y2020.day16;

import java.util.ArrayList;
import java.util.List;

public class Range {
    private final int start;
    private final int end;

    @SuppressWarnings("unused")
    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Range(String rangeString) {
        String[] bounds = rangeString.split("-");
        this.start = Integer.parseInt(bounds[0].strip());
        this.end = Integer.parseInt(bounds[1].strip());
    }

    public static List<Range> fromFields(String field) {
        List<Range> rangeList = new ArrayList<>();
        String[] ranges = field.split(" or ");
        for (String range: ranges) {
            rangeList.add(new Range(range));
        }
        return rangeList;
    }

    public boolean contains(int value) {
        return start <= value && value <= end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start+"-"+end;
    }
}
