package pushdown_automaton;

import pushdown_automaton.functions.Function;
import symbols.Concat;
import symbols.StackSymbol;
import symbols.TerminalSymbol;

import java.util.List;
import java.util.Stack;

public class PDA {
    private Stack<StackSymbol> stack;
    private List<State> states;
    private List<Function> functions;
    private State currentState;

    public PDA(List<State> states, List<Function> functions, State initialState) {
        this.stack = new Stack<>();
        this.stack.add(StackSymbol.empty());
        this.states = states;
        this.functions = functions;
        this.currentState = initialState;
    }

    private Function[] matchingFunctions() {
        return functions.stream()
                .filter(fn -> fn.start().state().equals(currentState))
                .toArray(Function[]::new);
    }

    private State advance(TerminalSymbol symbol) {
//        Optional<Function> fn
        throw new RuntimeException("Not implemented");
    }

    public boolean accepts(Concat word) {
        throw new RuntimeException("Not implemented");


    }


}
