package day08;

import day07.Bag;
import day07.Bags;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day08 {
    public static void main(String[] args) {
        URL url = Day08.class.getResource("input.txt");
        List<String> instructions = null;
        try {
            if (url == null) {
                throw new FileNotFoundException();
            }

            instructions = Files.readAllLines(Paths.get(url.toURI()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        List<Integer> candidates = new ArrayList<>();
        int acc = Day08.runMachine(instructions, candidates, true);
        System.out.println(acc);

        for (int candidate: candidates) {
            swapInstruction(instructions, candidate);
            acc = runMachine(instructions, candidates, false);
            if (acc != -1) {
                System.out.println(acc);
                return;
            }
            swapInstruction(instructions, candidate);
        }
    }

    private static void swapInstruction(List<String> instructions, int candidate) {
        String[] op = instructions.get(candidate - 1).split(" ");
        if (op[0].equals("jmp")) {
            instructions.set(candidate - 1, "nop " + Integer.parseInt(op[1]));
        } else if (op[0].equals("nop")) {
            instructions.set(candidate - 1, "jmp " + Integer.parseInt(op[1]));
        }
    }

    public static int runMachine(List<String> instructions, List<Integer> candidates, boolean initialRun)
    throws IllegalArgumentException {
        int pc = 1;
        int acc = 0;
        List<Integer> run = new ArrayList<>();
        run.add(instructions.size() + 1);

        while (!run.contains(pc)) {
            run.add(pc);
            String[] op = instructions.get(pc - 1).split(" ");
            if (!candidates.contains(pc) && initialRun && (op[0].equals("jmp") || op[0].equals("nop"))) {
                candidates.add(pc);
            }

            if (op[0].equals("nop")) {
                pc += 1;
            } else if  (op[0].equals("acc")) {
                acc += Integer.parseInt(op[1]);
                pc += 1;
            } else if  (op[0].equals("jmp")) {
                pc += Integer.parseInt(op[1]);
            }

        }
        if (!initialRun && pc != instructions.size() + 1) {
            return -1;
        }
        return acc;

    }
}
