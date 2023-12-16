package pushdown_automaton.functions;

import pushdown_automaton.State;
import symbols.StackSymbol;

/**
 * Represents the result of a function of a pushdown automaton.
 * @param state The state to go to.
 * @param stackSymbols The stack symbols to push.
 *
 * @author Matthias Harzer
 */
public record To(State state, StackSymbol... stackSymbols) {
}
