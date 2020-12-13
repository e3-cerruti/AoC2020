package day13;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day13 {
    private final InputStream dataFile;
    private List<Shuttle> shuttles;
    private int departureTime;

    public static void main(String[] args) {
        Day13 day = new Day13(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.processInput();
        System.out.format("Result of part 1 is %d.\n", day.findFirstShuttle());
        System.out.format("Result of part 2 is %d.\n", day.findEarliestTimestamp());
    }

    public Day13(String file) {
        dataFile = Day13.class.getResourceAsStream(file);
    }

    private void processInput() {
        try (Scanner input = new Scanner(dataFile)) {
            departureTime = input.nextInt();
            input.nextLine();
            List<String> shuttles = Arrays.stream(input.nextLine().split(","))
                    .collect(Collectors.toList());
            this.shuttles = IntStream.range(0, shuttles.size())
                    .mapToObj(n -> new Shuttle(shuttles.get(n), n))
                    .filter(Shuttle::isValid)
                    .collect(Collectors.toList());
        }
    }

    private long findFirstShuttle() {
        long firstShuttleTime = Integer.MAX_VALUE;
        int firstShuttle = -1;

        for (Shuttle shuttle : shuttles) {
            long time = shuttle.firstDepartureAfter(departureTime);
            if (time < firstShuttleTime) {
                firstShuttleTime = time;
                firstShuttle = shuttle.getNumber();
            }
        }
        System.out.format("The earliest departing bus is: %d at %d.\n", firstShuttle, firstShuttleTime);
        return firstShuttle * firstShuttleTime;
    }


    private long findEarliestTimestamp() {
        Optional<Long> intialDeparture = shuttles.stream().map(Shuttle::getInitialDeparture).max(Long::compareTo);
        long departure = intialDeparture.orElse(0L);
        long finalDeparture = departure;
        Stream<Boolean> departures = shuttles.stream().map(shuttle -> shuttle.departsInOrderAt(finalDeparture));
        List<Shuttle> departingShuttles = shuttles.stream().filter(shuttle -> shuttle.departsInOrderAt(finalDeparture)).collect(Collectors.toList());
        long numberOfDepartingShuttles = 0;
        long step = 0;
        while (!departures.allMatch(d -> d)) {
            if (numberOfDepartingShuttles != departingShuttles.size()) {
                numberOfDepartingShuttles = departingShuttles.size();
                step = departingShuttles
                        .stream()
                        .map(Shuttle::getNumber)
                        .mapToLong(Long::valueOf)
                        .reduce(1, (a, b) -> a * b);
            }
            departure += step;
            long finalDeparture1 = departure;
            departures = shuttles.stream().map(shuttle -> shuttle.departsInOrderAt(finalDeparture1));
            departingShuttles = shuttles.stream().filter(shuttle -> shuttle.departsInOrderAt(finalDeparture1)).collect(Collectors.toList());
        }

        return departure;
    }
}