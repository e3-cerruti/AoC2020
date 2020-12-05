package day05;

import java.util.ArrayList;

public class SegmentManager {
    private final ArrayList<Segment> segments = new ArrayList<>();

    public void addSeat(int seat) {
        int i = 0;
        boolean done = false;
        boolean startReplaced = false;
        boolean endReplaced = false;

        Segment firstSegment = null;
        Segment secondSegment = null;

        while (i < segments.size() && !done) {
            Segment segment = segments.get(i);
            if (seat == segment.getEnd() + 1) {
                endReplaced = true;
                firstSegment = segment;
                segment.setEnd(seat);
//                System.out.println("Segment Extended +");
            }

            if (seat == segment.getStart() - 1) {
                startReplaced = true;
                secondSegment = segment;
                segment.setStart(seat);
//                System.out.println("Segment Extended -");
            }


            if (startReplaced || segment.getStart() > seat) {
                done = true;
            } else {
                i += 1;
            }
        }
        if (startReplaced && endReplaced) {
//            System.out.println("Segment Merged");
            firstSegment.setEnd(secondSegment.getEnd());
            segments.remove(secondSegment);
        } else if (!startReplaced && !endReplaced) {
//            System.out.println("Segment Added");
            segments.add(i, new Segment(seat));
        }
    }

    public int getSeat() {
        if (segments.size() != 2) {
            throw new RuntimeException("Something went horribly wrong.");
        } else {
            if (segments.get(0).getEnd() + 1 == segments.get(1).getStart() - 1) {
                return segments.get(0).getEnd() + 1;
            } else {
                throw new RuntimeException("Something else went horribly wrong.");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Segment Manager: ");
        builder.append(segments.size());
        builder.append("\n");
        for (Segment segment: segments) {
            builder.append("\t");
            builder.append(segment.getStart());
            builder.append("-");
            builder.append(segment.getEnd());
            builder.append("\n");
        }

        return builder.toString();
    }
}
