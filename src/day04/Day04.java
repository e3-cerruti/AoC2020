package day04;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Day04 {;

    public static void main(String[] args) {
        InputStream dataFile = Day04.class.getResourceAsStream("test.txt");
        String passport = "";
        try (Scanner input = new Scanner(dataFile)) {
            int passportCount = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.isEmpty() || line.isBlank()) {
                    if (validPassport(passport.trim())) {
                        passportCount += 1;
                    }
                    passport = "";
                } else {
                    passport += line;
                }
            }
            if (validPassport(passport.trim())) {
                passportCount += 1;
            }
            System.out.println(passportCount);
        }
    }

    private static boolean validPassport(String passport) {
        System.out.println('['+passport+']');
        Map<String, String> map = new LinkedHashMap<String, String>();
        for(String keyValue : passport.split(" *\\s *")) {
            String[] pairs = keyValue.split(" *: *", 2);
            map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
        }

        // A passport may have 8 fields or 7 fields if cid is not present
        if ((map.size() != 8) || (map.size() != 7 && !map.containsKey("cid"))) {
            System.out.println("Wrong number of fields");
            return false;
        }

        System.out.println("Wrong number of fields");
        return true;
    }
}
