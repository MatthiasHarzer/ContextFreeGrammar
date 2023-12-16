package pushdown_automaton.functions;


import pushdown_automaton.State;
import symbols.StackSymbol;
import symbols.TerminalSymbol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a function of a pushdown automaton.
 *
 * @param start   The start of the function, represented with a start state, a terminal symbol and a stack symbol.
 * @param results The results of the function, represented with a state and one or multiple stack symbols.
 *
 * @author Matthias Harzer
 */
public record Function(From start, To... results) {

    /**
     * Creates a new function with the given start value.
     *
     * @return A new function with the given start value.
     */
    public static Function from(State state, TerminalSymbol terminalSymbol, StackSymbol stackSymbol) {
        return new Function(new From(state, terminalSymbol, stackSymbol));
    }

    /**
     * Returns a new function with the given results as one of the possible results.
     *
     * @param state        The state to go to.
     * @param stackSymbols The stack symbols to push.
     * @return A new function with the given results as one of the possible results.
     */
    public Function to(State state, StackSymbol... stackSymbols) {
        To[] newResults = Stream.concat(Arrays.stream(results), Stream.of(new To(state, stackSymbols)))
                .toArray(To[]::new);
        return new Function(start, newResults);
    }

    /**
     * Returns a new function with the given results as one of the possible results.
     *
     * @param state        The state to go to.
     * @param stackSymbols The stack symbols to push.
     * @return A new function with the given results as one of the possible results.
     */
    public Function or(State state, StackSymbol... stackSymbols) {
        return to(state, stackSymbols);
    }

    /**
     * Returns all states that are involved in this function.
     *
     * @return All states that are involved in this function.
     */
    public State[] getStates() {
        List<State> toStates = Arrays.stream(results)
                .map(To::state)
                .distinct()
                .toList();

        return Stream.concat(Stream.of(start.state()), toStates.stream()).toArray(State[]::new);
    }
}
