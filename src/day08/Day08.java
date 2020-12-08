package day08;

import machine.HandheldGameConsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day08 {
    public static void main(String[] args) {
        URL url = Day08.class.getResource("input.txt");
        List<String> instructions = new ArrayList<>();
        try {
            if (url == null) {
                throw new FileNotFoundException();
            }

            instructions = Files.readAllLines(Paths.get(url.toURI()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        // Execute the program and determine the accumulator when the first instruction repeats
        HandheldGameConsole console = new HandheldGameConsole();
        console.loadProgram(instructions);
        console.runProgram();
        System.out.println(console.getAccumulator());

        List<Integer> possiblyCorrupted = console.getInstructionsRun();

        for  (int currentTest : possiblyCorrupted) {
            if (instructions.get(currentTest - 1).startsWith("acc")) {
                continue;
            }

            swapInstruction(instructions, currentTest);
            console.loadProgram(instructions);
            console.runProgram();
            int programCounter = console.getProgramCounter();
            if (programCounter >= instructions.size()) {
                System.out.println(console.getAccumulator());
                break;
            }
            swapInstruction(instructions, currentTest);
        }
    }

    /**
     * changes jmp instructions to nop instructions and nop instructions to jmp instructions
     *
     * @param instructions list of program instructions
     * @param lineNumber program line number to change (1 based)
     */
    private static void swapInstruction(List<String> instructions, int lineNumber) {
        String instruction = instructions.get(lineNumber - 1);
        String operation = instruction.substring(0, 3);
        if (operation.equals("jmp")) {
            instructions.set(lineNumber - 1, "nop" + instruction.substring(3));
        } else if (operation.equals("nop")) {
            instructions.set(lineNumber - 1, "jmp" + instruction.substring(3));
        }
    }
}
