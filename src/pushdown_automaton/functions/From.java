package pushdown_automaton.functions;

import pushdown_automaton.State;
import symbols.StackSymbol;

import symbols.Symbol;
import symbols.TerminalSymbol;

/**
 * Represents the start of a function of a pushdown automaton.
 * @param state The state to start from.
 * @param terminalSymbol The terminal symbol to start from.
 * @param stackSymbol The top stack symbol to start from.
 *
 * @author Matthias Harzer
 */
public record From(State state, TerminalSymbol terminalSymbol, StackSymbol stackSymbol) {
    public boolean matches(State state, Symbol terminalSymbol, Symbol stackSymbol){
        return state().equals(state)
                && terminalSymbol().equals(terminalSymbol)
                && stackSymbol().equals(stackSymbol);
    }
}
