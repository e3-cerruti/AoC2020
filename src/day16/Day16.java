package day16;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    private final List<Field> fields;
    private final Scanner input;
    private List<List<Integer>> validRanges;
    private static List<Integer> myTicket;
    private final static Map<Integer, List<Field>> fieldMap = new HashMap<>();

    public static void main(String[] args) {
        Day16 day = new Day16(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.readFields();
        myTicket = day.readMyTicket();
        System.out.format("Part 1: %d\n", day.processScannedInput());
        day.reduceFieldMap();
        System.out.format("Par 2: %d\n", day.processMyTicket());
    }

    public Day16(String file) {
        InputStream dataFile = Day16.class.getResourceAsStream(file);
        input = new Scanner(dataFile);
        fields = new ArrayList<>();
    }

    private List<Integer> readMyTicket() {
        String myTicket;
        input.nextLine();
        myTicket = input.nextLine();
        input.nextLine();
        System.out.println(myTicket);

        ArrayList<Integer> result = new ArrayList<>();
        for (String field : myTicket.split(",")) {
            result.add(Integer.parseInt(field.strip()));
        }

        return result;
    }

    private void readFields() {
        String fieldString = input.nextLine();
        while (!fieldString.isBlank()) {
            Field field = new Field(fieldString);
            System.out.println(field);
            fields.add(field);
            fieldString = input.nextLine();
        }

        for (int i = 0; i < fields.size(); i++) {
            fieldMap.put(i, new ArrayList<>(fields));
        }
        validRanges = Field.allValidRanges(fields);
        for (List<Integer> range : validRanges) {
            System.out.format("%d-%d\n", range.get(0), range.get(1));
        }

    }

    private int processScannedInput() {
        int totalErrors = 0;
        input.nextLine();
        while (input.hasNextLine()) {
            String ticket = input.nextLine();
            String[] ticketFields = ticket.split(",");
            for (int i = 0; i < ticketFields.length; i++) {
                int value = Integer.parseInt(ticketFields[i]);
                List<Field> invalidFields = new ArrayList<>();
                if (Field.validFor(validRanges, value)) {
                    for (Field field : fields) {
                        if (fieldMap.get(i).contains(field) && !field.valueInRange(value)) {
                            invalidFields.add(field);
                        }
                    }
                    fieldMap.get(i).removeAll(invalidFields);
                } else {
//                    System.out.format("Error %d: %d\n", i, value);
                    totalErrors += value;
                }
            }

        }

        return totalErrors;
    }

    private void reduceFieldMap() {
        boolean removed = true;
        while (removed) {
            removed = false;

            for (int i : fieldMap.keySet()) {
                if (fieldMap.get(i).size() == 1) {
                    removed |= removeFromMap(fieldMap.get(i).get(0));
                }
            }
        }

        // Prints the field map
        for (int i : fieldMap.keySet()) {
            List<Field> fields = fieldMap.get(i);
            System.out.format("%d: %s\n", i,
                    fields
                    .stream()
                    .map(Field::getName)
                    .collect(Collectors.joining(",")));
        }
    }

    private boolean removeFromMap(Field field) {
        boolean removed = false;
        for (int fieldNumber : fieldMap.keySet()) {
            if (fieldMap.get(fieldNumber).size() > 1) {
                removed |= fieldMap.get(fieldNumber).remove(field);
            }
        }
        return removed;
    }

    private long processMyTicket() {
        long result = 1;
        for (int i : fieldMap.keySet()) {
            if (fieldMap.get(i).get(0).getName().startsWith("departure")) {
                System.out.format("%s: %d\n", fieldMap.get(i).get(0), myTicket.get(i));
                result *= myTicket.get(i);
            }
        }
        return result;
    }
}
