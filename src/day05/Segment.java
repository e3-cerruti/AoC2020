package day05;

public class Segment {
    private int start;
    private int end;

    public Segment(int seat) {
        this.start = seat;
        this.end = seat;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
