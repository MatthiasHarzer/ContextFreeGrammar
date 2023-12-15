package pushdown_automaton;

import symbols.Word;


public record Configuration(State state, Word input, Stack stack) {
    public boolean accepted() {
        return input.isEmpty() && stack.isEmpty();
    }

    public boolean valid() {
        // If the input is empty, the stack must be empty too.
        return input.isEmpty() == stack.isEmpty();
    }
}
