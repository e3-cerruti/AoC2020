package day18;

import java.io.InputStream;
import java.util.*;

public class Day18 {
    private final InputStream dataFile;

    public Day18(String fileName) {
        dataFile = Day18.class.getResourceAsStream(fileName);
    }


    public static void main(String[] args) {
        Day18 day = new Day18(args.length >= 1 && args[0] != null ? args[0] : "test.txt");
        day.processFile();
    }

    public void processFile() {
        Scanner input = new Scanner(dataFile);

        long sum = 0;

        while (input.hasNextLine()) {
            sum += processLine(input.nextLine());
        }

        System.out.format("Part 1: The sum of all lines is %d\n", sum);
    }

    public long processLine(String line) {
        long accumulator;
        long testResult2 = Long.MIN_VALUE;
        boolean testResultPresent = false;
        long result = 0;

        String[] tokens = line
                .replaceAll("\\(", "( ")
                .replaceAll("\\)", " )")
                .split(" ");

        if (tokens[tokens.length-3].equals("becomes")) {
            testResultPresent = true;
            long testResult1 = Long.parseLong(tokens[tokens.length - 2]);
            testResult2 = Long.parseLong(tokens[tokens.length - 1]);
            tokens = Arrays.copyOfRange(tokens, 0, tokens.length - 2);
        }
        String[] rpn = infixToRPN(tokens);

        for (String token : rpn) {
            System.out.print(token + " ");
        }

        Stack<Long> stack = new Stack<>();
        for (String term: rpn) {
            if (term.equals( "+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (term.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else {
                stack.push(Long.parseLong(term));
            }
        }
        result = stack.pop();

        if (testResultPresent) {
            if (result == testResult2) {
                System.out.format("Sum %d is correct.\n", result);
            } else {
                System.out.format("Sum %d is incorrect, correct result is %d.\n", result, testResult2);
            }
        } else {
            System.out.format("Sum %d.\n", result);
        }

        return result;
    }


    /**
     * Shunting yard algorithm from https://andreinc.net/2010/10/05/converting-infix-to-rpn-shunting-yard-algorithm/
     *
     * Modified for precedence rules
     */
    public static String[] infixToRPN(String[] inputTokens) {
        ArrayList<String> out = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        // For all the input tokens [S1] read the next token [S2]
        for (String token : inputTokens) {
            if (isOperator(token)) {
                // If token is an operator (x) [S3]
                while (!stack.empty() && isOperator(stack.peek())) {
                    // [S4]
                    if ((isAssociative(token, LEFT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) <= 0)
                            || (isAssociative(token, RIGHT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) < 0)) {
                        out.add(stack.pop()); 	// [S5] [S6]
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack [S7]
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token); 	// [S8]
            } else if (token.equals(")")) {
                // [S9]
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop()); // [S10]
                }
                stack.pop(); // [S11]
            } else {
                out.add(token); // [S12]
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop()); // [S13]
        }
        String[] output = new String[out.size()];
        return out.toArray(output);
    }

    // Associativity constants for operators
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;

    // Supported operators
    private static final Map<String, int[]> OPERATORS = new HashMap<String, int[]>();
    static {
        // Map<"token", []{precendence, associativity}>
        OPERATORS.put("+", new int[] { 5, LEFT_ASSOC }); // was 0
        OPERATORS.put("-", new int[] { 5, LEFT_ASSOC }); // was 0
        OPERATORS.put("*", new int[] { 0, LEFT_ASSOC }); // Was: 5
        OPERATORS.put("/", new int[] { 0, LEFT_ASSOC }); // Was: 5
//        OPERATORS.put("%", new int[] { 5, LEFT_ASSOC });
//        OPERATORS.put("^", new int[] { 10, RIGHT_ASSOC });
    }

    /**
     * Test if a certain is an operator .
     * @param token The token to be tested .
     * @return True if token is an operator . Otherwise False .
     */
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    /**
     * Test the associativity of a certain operator token .
     * @param token The token to be tested (needs to operator).
     * @param type LEFT_ASSOC or RIGHT_ASSOC
     * @return True if the tokenType equals the input parameter type .
     */
    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }
        if (OPERATORS.get(token)[1] == type) {
            return true;
        }
        return false;
    }

    /**
     * Compare precendece of two operators.
     * @param token1 The first operator .
     * @param token2 The second operator .
     * @return A negative number if token1 has a smaller precedence than token2,
     * 0 if the precendences of the two tokens are equal, a positive number
     * otherwise.
     */
    private static final int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalied tokens: " + token1
                    + " " + token2);
        }
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }
}
