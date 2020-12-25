package com.e3civichigh.aoc.y2020.day25;

public class Day25 {
    public static long SUBJECT = 7;

    public static void main(String[] args) {
        Day25 day = new Day25();
        System.out.println(day.runPart1());
    }

    public long runPart1() {
//        long cardPublicKey = 5764801L;
//        long doorPublicKey = 17807724;
        long cardPublicKey = 11349501;
        long doorPublicKey = 5107328;

        long cardLoopSize = transform(7, cardPublicKey);
        long doorLoopSize = transform(7, doorPublicKey);

        return encrypt(cardPublicKey, doorLoopSize);
    }

    public long transform(long subject, long publicKey) {
        long value = subject;
        int i = 0;
        while (value != publicKey) {
            // Set the value to itself multiplied by the subject number.
            value *= subject;
            // Set the value to the remainder after dividing the value by 20201227.
            value %= 20201227;
            i++;
        }
        return i;
    }

    public long encrypt(long key, long loop) {
        long value = key;
        for (int i = 0; i < loop; i++) {
            value *= key;
            value %= 20201227;
        }
        return value;
    }
}
