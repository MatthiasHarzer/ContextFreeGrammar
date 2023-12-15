package pushdown_automaton.functions;

import pushdown_automaton.State;
import symbols.StackSymbol;

public record To(State state, StackSymbol... stackSymbols) {
}
