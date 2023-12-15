package pushdown_automaton.functions;

import pushdown_automaton.State;
import symbols.StackSymbol;

import symbols.TerminalSymbol;

public record From(State state, TerminalSymbol symbol, StackSymbol stackSymbol) {
}
