package Day16;

import java.io.InputStream;
import java.util.*;

public class Day16 {
    private final List<Field> fields;
    private final Scanner input;
//    private List<List<Integer>> validRanges;
    private static List<Integer> myTicket;
    private final static Map<Integer, List<String>> fieldMap = new HashMap<>();

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
        List<String> fieldNames = new ArrayList<>();
        while (!fieldString.isBlank()) {
            Field field = new Field(fieldString);
            System.out.println(field);
            fields.add(field);
            fieldNames.add(field.getName());
            fieldString = input.nextLine();
        }

        for (int i = 0; i < fields.size(); i++) {
            fieldMap.put(i, new ArrayList<>(fieldNames));
        }
//        validRanges = Field.allValidRanges(fields);

    }

    private int processScannedInput() {
        int totalErrors = 0;
        input.nextLine();
        while (input.hasNextLine()) {
            String ticket = input.nextLine();
            String[] ticketFields = ticket.split(",");
            for (int i = 0; i < ticketFields.length; i++) {
                int value = Integer.parseInt(ticketFields[i]);
                List<String> invalidFields = new ArrayList<>();
//                for (List<Integer> range: validRanges) {
//                    if (range.get(0) <= value && value <= range.get(1)) {
//                        valid = true;
//                        break;
//                    }
//                }
                for (Field field: fields) {
                    if (fieldMap.get(i).contains(field.getName()) && !field.valueInRange(value)) {
                        invalidFields.add(field.getName());
                    }
                }
                if (invalidFields.size() == fieldMap.get(i).size()) {
                    System.out.format("Error %d: %d\n", i, value);
                    totalErrors += value;
                } else {
                    fieldMap.get(i).removeAll(invalidFields);
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

        for (int i : fieldMap.keySet()) {
            List<String> fields = fieldMap.get(i);
            System.out.format("%d: %s\n", i, String.join(",", fields));
        }
    }

    private boolean removeFromMap(String field) {
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
            if (fieldMap.get(i).get(0).startsWith("departure")) {
                System.out.format("%s: %d\n", fieldMap.get(i).get(0), myTicket.get(i));
                result *= myTicket.get(i);
            }
        }
        return result;
    }
}
