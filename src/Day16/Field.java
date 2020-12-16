package Day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {
    private final String name;
    private final List<Range> ranges;

    public Field(String fieldDefinition) {
        String[] parts = fieldDefinition.split(":");
        this.name = parts[0];
        this.ranges = Range.fromFields(parts[1]);
    }

    public boolean valueInRange(int value) {
        for (Range range : ranges) {
            if (range.contains(value)) return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static List<List<Integer>> allValidRanges(List<Field> fields) {
        ArrayList<List<Integer>> result = new ArrayList<>();


        for (Field field: fields) {
            for (Range range : field.ranges) {
                int start = range.getStart();
                int end = range.getEnd();

                boolean startContained = false;
                boolean endContained = false;
                int startPosition = 0;
                int endPosition = 0;
                for (int i = 0; i < result.size(); i++) {
                    int resultStart = result.get(i).get(0);
                    int resultEnd = result.get(i).get(1);

                    if (resultStart <= start && start <= resultEnd) {
                        startContained = true;
                        startPosition = i;
                    } else if (start < resultStart) {
                        startPosition = i;
                    }

                    if (resultStart <= end && end <= resultEnd) {
                        endContained = true;
                        endPosition = i;
                    } else if (end < resultStart) {
                        endPosition = i;
                    }
                }

                if (startPosition == endPosition) {
                    if (!startContained && !endContained) {
                        result.add(startPosition, Arrays.asList(start, end));
                    } else if (startContained) {
                        result.get(startPosition).set(1, end);
                    } else if (endContained) { // why must start contained be true?
                        result.get(startPosition).set(0, start);
                    }
                } else {
                    if (startContained && endContained) {
                        result.get(endPosition).set(0, result.get(startPosition).get(0));
                        List<List<Integer>> toRemove = new ArrayList<>();
                        for (int i = startPosition; i < endPosition; i++) {
                            toRemove.add(result.get(i));
                        }
                        result.removeAll(toRemove);
                    } else if (startContained) {
                        result.get(endPosition).set(0, result.get(startPosition).get(0));
                        result.get(endPosition).set(1, end);
                        List<List<Integer>> toRemove = new ArrayList<>();
                        for (int i = startPosition; i < endPosition; i++) {
                            toRemove.add(result.get(i));
                        }
                        result.removeAll(toRemove);
                    } else if (endContained) {
                        result.get(endPosition).set(0, start);
                    }
                    List<List<Integer>> toRemove = new ArrayList<>();
                    for (int i = startPosition; i < endPosition; i++) {
                        toRemove.add(result.get(i));
                    }
                    result.removeAll(toRemove);
                }
            }
        }
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder field = new StringBuilder(name);
        field.append(": ");
        boolean firstRange = true;
        for (Range range: ranges) {
            if (firstRange) {
                firstRange = false;
            } else {
                field.append(" or ");
            }
            field.append(range.toString());
        }
        return field.toString();
    }
}
