package examples;

import context_free_acceptor.ContextFreeAcceptor;
import symbols.Word;

/**
 * A simple test class for {@link ContextFreeAcceptor ContextFreeAcceptors}.
 *
 * @param input    The word to be accepted.
 * @param expected Whether the word should be accepted.
 */
public record TestCFA(String input, boolean expected) {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void test(ContextFreeAcceptor cfa, Word word){
        test(cfa, word, true);
    }

    public static void test(ContextFreeAcceptor cfa, String word){
        test(cfa, word, true);
    }

    public static void test(ContextFreeAcceptor cfa, Word word, boolean expected) {
        long start = System.currentTimeMillis();
        boolean actual = cfa.accepts(word);
        long end = System.currentTimeMillis();
        printOutput(actual, expected, word.toString(), end - start);
    }

    public static void test(ContextFreeAcceptor cfa, String word, boolean expected) {
        long start = System.currentTimeMillis();
        boolean actual = cfa.accepts(word);
        long end = System.currentTimeMillis();
        printOutput(actual, expected, word, end - start);
    }

    private static void printOutput(boolean actual, boolean expected, String input, long time) {
        String out;
        if (actual == expected) {
            out = "[" + ANSI_GREEN + "PASSED" + ANSI_RESET + "] " + input + " -> " + actual + " (expected: " + expected + ")";
        } else {
            out = "[" + ANSI_RED + "FAILED" + ANSI_RESET + "] " + input + " -> " + actual + " (expected: " + expected + ")";
        }

        System.out.println(out + " (" + time + "ms)");
    }

    public static void testCFA(ContextFreeAcceptor cfa, TestCFA... tests) {
        for (TestCFA test : tests) {
            boolean actual = cfa.accepts(test.input());
            if (actual == test.expected()) {
                System.out.println("[" + ANSI_GREEN + "PASSED" + ANSI_RESET + "] " + test.input() + " -> " + actual + " (expected: " + test.expected() + ")");
            } else {
                System.out.println("[" + ANSI_RED + "FAILED" + ANSI_RESET + "] " + test.input() + " -> " + actual + " (expected: " + test.expected() + ")");
            }
        }
    }
}
