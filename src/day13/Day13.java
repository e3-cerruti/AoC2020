package day13;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day13 {
    private final InputStream dataFile;
    private List<Bus> busses;
    private int departureTime;

    public static void main(String[] args) {
        Day13 day = new Day13(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.processInput();
        System.out.format("Result of part 1 is %d.\n", day.findFirstBus());
        System.out.format("Result of part 2 is %d.\n", day.findEarliestTimestamp());
    }

    public Day13(String file) {
        dataFile = Day13.class.getResourceAsStream(file);
    }

    private void processInput() {
        try (Scanner input = new Scanner(dataFile)) {
            departureTime = input.nextInt();
            input.nextLine();
            List<String> busses = Arrays.stream(input.nextLine().split(","))
                    .collect(Collectors.toList());
            this.busses = IntStream.range(0, busses.size())
                    .mapToObj(n -> new Bus(busses.get(n), n))
                    .filter(Bus::isValid)
                    .collect(Collectors.toList());
        }
    }

    private long findFirstBus() {
        long firstBusTime = Integer.MAX_VALUE;
        int firstBus = -1;

        for (Bus bus : busses) {
            long time = bus.firstDepartureAfter(departureTime);
            if (time < firstBusTime) {
                firstBusTime = time;
                firstBus = bus.getNumber();
            }
        }
        System.out.format("The earliest departing bus is: %d at %d.\n", firstBus, firstBusTime);
        return firstBus * firstBusTime;
    }


    private long findEarliestTimestamp() {
        Optional<Long> intialDeparture = busses.stream().map(Bus::getInitialDeparture).max(Long::compareTo);
        long departure = intialDeparture.orElse(0L);
        long finalDeparture = departure;
        Stream<Boolean> departures = busses.stream().map(bus -> bus.departsInOrderAt(finalDeparture));
        List<Bus> departingBusses = busses.stream().filter(bus -> bus.departsInOrderAt(finalDeparture)).collect(Collectors.toList());
        long numberOfDepartingBusses = 0;
        long step = 0;
        while (!departures.allMatch(d -> d)) {
            if (numberOfDepartingBusses != departingBusses.size()) {
                numberOfDepartingBusses = departingBusses.size();
                step = departingBusses
                        .stream()
                        .map(Bus::getNumber)
                        .mapToLong(Long::valueOf)
                        .reduce(1, (a, b) -> a * b);
            }
            departure += step;
            long finalDeparture1 = departure;
            departures = busses.stream().map(bus -> bus.departsInOrderAt(finalDeparture1));
            departingBusses = busses.stream().filter(bus -> bus.departsInOrderAt(finalDeparture1)).collect(Collectors.toList());
        }

        return departure;
    }
}

            /*
            List<Integer> busses = Arrays.stream(
                    input.nextLine().split(","))
                    .filter(b -> !b.equals("x"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            int soonest = Integer.MAX_VALUE;
            int nextBus = 0;
            for (int bus: busses) {
                int nextTime = bus - departureTime % bus;
                System.out.format("%d %d\n", bus, nextTime);
                if (nextTime < soonest) {
                    nextBus = bus;
                    soonest = nextTime;
                }
            }

            System.out.format("%d %d %d\n", nextBus, soonest, nextBus * soonest);
            */
/*
            List<String> busses = Arrays.stream(input.nextLine().split(",")).collect(Collectors.toList());
            int size = (int) busses.stream().filter(b -> !b.equals("x")).mapToInt(Integer::parseInt).max().getAsInt()+1;

            int[] depths = new int[size];
            for (int i = 0; i < busses.size(); i ++) {
                if (!busses.get(i).equals("x")) {
                    depths[Integer.parseInt(busses.get(i))] = i;
                }
            }

            int step = size - 1;
            int[] busNumbers = busses.stream().filter(b -> !b.equals("x")).mapToInt(Integer::parseInt).sorted().toArray();
            for( int i = 0; i < busNumbers.length/2; ++i )
            {
                int temp = busNumbers[i];
                busNumbers[i] = busNumbers[busNumbers.length - i - 1];
                busNumbers[busNumbers.length - i - 1] = temp;
            }

            long epoch = -depths[step];
            long oldStep = step;
            boolean found = false;
            while (!found) {
                long newStep = 1;
                epoch += oldStep;
                found = true;
                System.out.format("%d: ", epoch);
                for (int bus : busNumbers) {
                    System.out.format("(b:%d o:%d): %d\t", bus, depths[bus], (epoch + depths[bus]) % bus);
                    if ((epoch + depths[bus]) % bus != 0) {
                        found = false;
                    } else {
                        newStep = newStep * bus;
                    }
                }
                if (newStep > oldStep) {
                    oldStep = newStep;
                    System.out.print("new step: "+ newStep);
                }
                System.out.println();
            }
            System.out.println(epoch + " " + (epoch == departureTime));

            //System.out.println(findEpoch(busses, Integer.parseInt(busses.get(0)), 0));
//            int bus = Integer.parseInt(busses.get(0));
//            long epoch = bus;
//            while (checkNextBus(epoch, busses.subList(1, busses.size()), 1)) {
//                epoch += bus;
//            }
//            System.out.println(epoch);

        }
    }

    public static boolean areAllTrue(boolean[] array)
    {
        for(boolean b : array) if(!b) return false;
        return true;
    }

    private boolean checkNextBus(long epoch, List<String> busses, int depth) {
        if (busses.size() <= 0) return false;
        else if (!busses.get(0).equals("x") && (epoch + depth) % Integer.parseInt(busses.get(0)) != 0) return true;
        else return checkNextBus(epoch, busses.subList(1, busses.size()), depth + 1);
    }

    private long findEpoch(List<String> busses, long epoch, int depth) {
        if (busses.size() <= 0) return epoch;

        if (busses.get(0).equals("x")) {
            return findEpoch(busses.subList(1, busses.size()), epoch, depth+1);
        }
        else {
            long n = 1;
            long localEpoch = epoch;
            int bus = Integer.parseInt(busses.get(0));
            long offset = (epoch + depth) % bus;
            while (localEpoch < bus || offset != 0) {
                n++;
                localEpoch = epoch * n;
                offset = (localEpoch + depth) % bus;
                System.out.format("Bus: %d Depth: %d Epoch: %d Offset: %d\n", bus, depth, localEpoch, offset);
            }
            return findEpoch(busses.subList(1, busses.size()), localEpoch, depth+1);
        }


    }

}


 */