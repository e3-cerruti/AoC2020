package com.e3civichigh.aoc.y2020.machine;

import java.util.*;
import java.util.function.Consumer;

public class HandheldGameConsole {
    private static final int OPERATION = 0;
    private int programCounter;
    private int accumulator;

    private boolean terminateOnRepeatedInstruction = true;

    private final List<Integer> instructionsRun = new ArrayList<>();
    private List<String> instructions;

    private final Map<String, Consumer<String[]>> instructionSet = new HashMap<>();

    public HandheldGameConsole() {
        instructionSet.put("nop", this::nop);
        instructionSet.put("acc", this::acc);
        instructionSet.put("jmp", this::jmp);
    }

    private void jmp(String[] operands) {
        programCounter += Integer.parseInt(operands[0]);
    }

    private void acc(String[] operands) {
        accumulator += Integer.parseInt(operands[0]);
        programCounter += 1;
    }

    private void nop(String[] operands) {
        programCounter += 1;
    }

    public void loadProgram(List<String> instructions) {
        this.instructions = instructions;
    }

    public void runProgram() {
        programCounter = 1;
        accumulator = 0;

        instructionsRun.clear();

        while (true) {
            instructionsRun.add(programCounter);

            String[] op = instructions.get(programCounter - 1).split(" ");
            String operation = op[OPERATION];
            String[] operands = Arrays.copyOfRange(op, 1, op.length);

            instructionSet.get(operation).accept(operands);

            if (!programCounterValid() || programRepeating()) {
                return;
            }
        }
    }

    private boolean programRepeating() {
        return terminateOnRepeatedInstruction && instructionsRun.contains(programCounter);
    }

    private boolean programCounterValid() {
        return 1 <= programCounter && programCounter <= instructions.size();
    }

    @SuppressWarnings("unused")
    public void setTerminateOnRepeatedInstruction(boolean terminateOnRepeatedInstruction) {
        this.terminateOnRepeatedInstruction = terminateOnRepeatedInstruction;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public int getAccumulator() {
        return accumulator;
    }

    public List<Integer> getInstructionsRun() {
        return new ArrayList<>(instructionsRun);
    }
}
