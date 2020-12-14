package day14;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
    private final InputStream dataFile;
    Map<Long, Long> memory = new HashMap<>();

    Pattern maskPattern = Pattern.compile("mask\\s=\\s([01X]+)");
    Pattern memoryPattern = Pattern.compile("mem\\[(\\d+)]\\s=\\s(\\d+)");

    long andMask = 0L;
    long orMask = 0L;
    long xMask = 0L;

    ArrayList<Long> masks = new ArrayList<>();

    public static void main(String[] args) {
        Day14 day = new Day14(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.processInput();
    }

    public Day14(String file) {
        dataFile = Day14.class.getResourceAsStream(file);
    }

    private void processInput() {
        try (Scanner input = new Scanner(dataFile)) {
            while (input.hasNextLine()) {
                version2(input.nextLine());
            }
        }
        System.out.println(memory.values().stream().reduce(Long::sum).orElse(-1L));
    }

    private void initialization(String instruction) {
        Matcher maskMatcher = maskPattern.matcher(instruction);
        Matcher memoryMatcher = memoryPattern.matcher(instruction);
        if (maskMatcher.find()) {
            String mask = maskMatcher.group(1);
            andMask = Long.parseLong(mask.replaceAll("X", "1"), 2);
            orMask = Long.parseLong(mask.replaceAll("X", "0"), 2);
        } else if (memoryMatcher.find()) {
            Long mem = Long.parseLong(memoryMatcher.group(1));
            int value = Integer.parseInt(memoryMatcher.group(2));

            long data;
            data = andMask & value;
            data = orMask | data;
            memory.put(mem, data);
        }
    }

    private void version2(String instruction) {
        Matcher maskMatcher = maskPattern.matcher(instruction);
        Matcher memoryMatcher = memoryPattern.matcher(instruction);

        if (maskMatcher.find()) {
            String maskString = maskMatcher.group(1);
            orMask = Long.parseLong(maskString.replaceAll("X", "0"), 2);
            xMask = Long.parseLong(
                    maskString.replaceAll("1", "0").replaceAll("X", "1"), 2);

            masks.clear();
            masks.add(0L);
            long positionValue;

            for (int n = 0; n < 36; n++) {
                positionValue = 1L << n;
                if ((positionValue & xMask) == 0) continue;

                ArrayList<Long> newMasks = new ArrayList<>();
                for (Long mask : masks) {
                    newMasks.add(mask | positionValue);
                }
                masks.addAll(newMasks);
            }
            xMask ^= 0xFFFF;
        } else if (memoryMatcher.find()) {
            long mem = Long.parseLong(memoryMatcher.group(1));
            long value = Long.parseLong(memoryMatcher.group(2));

            mem = mem & xMask | orMask;
            for (Long mask : masks) {
                long location = (mem | orMask) | mask;
                memory.put(location, value);
            }
        }
    }
}