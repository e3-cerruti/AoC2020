package day02;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day02 {
    public static void main(String[] args) {
        List<PasswordRecord> records;
        try {
            URL url = Day02.class.getResource("input.txt");
            if (url == null) {
                throw new FileNotFoundException();
            }

            records = Files.readAllLines(Paths.get(url.toURI()))
                    .stream()
                    .map(PasswordRecord::parseRecordRegex)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        long validOld = records.stream().filter(PasswordRecord::isValidOldPolicy).count();
        long validNew = records.stream().filter(PasswordRecord::isValidNewPolicy).count();
        System.out.println("Valid (old): " + validOld);
        System.out.println("Valid (new): " + validNew);
    }
}
