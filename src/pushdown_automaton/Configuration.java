package pushdown_automaton;

import symbols.Word;

/**
 * A configuration of a pushdown automaton. This is a snapshot of the automaton at a certain point in time.
 * @param state The current state.
 * @param input The remaining input.
 * @param stack The current stack.
 *
 * @author Matthias Harzer
 */
public record Configuration(State state, Word input, Stack stack) {
    /**
     * The configuration is in an accepting state if the input is empty and the stack is empty.
     * @return true if the configuration is in an accepting state
     */
    public boolean accepted() {
        return input.isEmpty() && stack.isEmpty();
    }

    /**
     * The configuration is valid if the input is empty if and only if the stack is empty. Invalid configurations
     * should not be used.
     * @return true if the configuration is valid
     */
    public boolean valid() {
        // If the input is empty, the stack must be empty too.
        return input.isEmpty() == stack.isEmpty();
    }
}
