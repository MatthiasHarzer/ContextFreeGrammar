package examples;

import context_free_acceptor.ContextFreeAcceptor;

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
