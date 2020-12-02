package day02;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PasswordRecord {
    private final int start;
    private final int end;
    private final char letter;
    private final String password;

    private PasswordRecord(int start, int end, char letter, String password) {
        this.start = start;
        this.end = end;
        this.letter = letter;
        this.password = password;
    }

    @SuppressWarnings("unused")
    public static PasswordRecord parseRecordSplit(String line) {
        String[] parts = line.split(" ");
        List<Integer> indices = Arrays
                .stream(parts[0].split("-"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        char letter = parts[1].charAt(0);
        return new PasswordRecord(
                indices.get(0),
                indices.get(1),
                letter,
                parts[2]);
    }

    @SuppressWarnings("unused")
    public static PasswordRecord parseRecordRegex(String line) {
        String pattern = "(\\d+)-(\\d+)\\s(.):\\s(.+)";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);

        if (m.find()) {
            return new PasswordRecord(
                    Integer.parseInt(m.group(1)),
                    Integer.parseInt(m.group(2)),
                    m.group(3).charAt(0),
                    m.group(4));
        } else {
            // Note that this is very dangerous and shouldn't be done.
            return null;
        }
    }

    public boolean isValidOldPolicy() {
//        long countOfLetter = password.chars().filter(c -> c == letter).count();
        long countOfLetter = 0;
        for (char c : password.toCharArray()) {
            if (c == letter) {
                countOfLetter += 1;
            }
        }

        return (start <= countOfLetter) && (countOfLetter <= end);
    }

    public boolean isValidNewPolicy() {
        return (password.charAt(start-1) == letter) != (password.charAt(end-1) == letter);
    }

    @Override
    public String toString() {
        return String.format("%d-%d %s: %s", start, end, letter, password);
    }
}
