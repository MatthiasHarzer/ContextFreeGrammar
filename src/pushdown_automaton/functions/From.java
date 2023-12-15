package pushdown_automaton.functions;

import pushdown_automaton.State;
import symbols.StackSymbol;

import symbols.Symbol;
import symbols.TerminalSymbol;

public record From(State state, TerminalSymbol terminalSymbol, StackSymbol stackSymbol) {
    public boolean matches(State state, Symbol terminalSymbol, Symbol stackSymbol){
        return state().equals(state)
                && terminalSymbol().equals(terminalSymbol)
                && stackSymbol().equals(stackSymbol);
    }
}
