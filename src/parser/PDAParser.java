package parser;

import pushdown_automaton.PDA;
import pushdown_automaton.State;
import pushdown_automaton.functions.From;
import pushdown_automaton.functions.To;
import symbols.StackSymbol;
import symbols.TerminalSymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A parser for {@link PDA Pushdown Automata}. It is able to parse a string representation of a PDA and create a {@link PDA} object from it.
 * Note: <code>ε</code> can be used as the epsilon symbol throughout the parser.
 *
 * @author Matthias Harzer
 */
public class PDAParser {
    private final String stackStartSymbol;
    private String epsilon = "ε";
    private final List<StackSymbol> stackSymbols;
    private final List<State> states;
    private final List<TerminalSymbol> terminals;
    private final PDA pda;


    public PDAParser(String startState, String stackStartSymbol, String... stackSymbols) {
        this.stackStartSymbol = stackStartSymbol;
        this.stackSymbols = Stream.of(stackSymbols)
                .map(StackSymbol::new)
                .toList();


        this.states = new ArrayList<>();
        this.terminals = new ArrayList<>();
        this.pda = new PDA(getState(startState));
    }

    /**
     * Set the string that is used as the epsilon symbol.
     * @param epsilon the string that is used as the epsilon symbol
     */
    public void setEpsilon(String epsilon) {
        this.epsilon = epsilon;
    }

    private StackSymbol getStackSymbol(String identifier) {
        String strippedIdentifier = identifier.strip();
        if (strippedIdentifier.equals(epsilon)) {
            return StackSymbol.EPSILON;
        }
        if (strippedIdentifier.equals(stackStartSymbol)) {
            return StackSymbol.START_SYMBOL;
        }
        return stackSymbols.stream()
                .filter(s -> s.identifier.equals(strippedIdentifier))
                .findFirst()
                .orElseThrow();
    }

    private State getState(String identifier) {
        String strippedIdentifier = identifier.strip();
        return states.stream()
                .filter(s -> s.identifier().equals(strippedIdentifier))
                .findFirst()
                .orElseGet(() -> {
                    State state = new State(strippedIdentifier);
                    states.add(state);
                    return state;
                });
    }

    private TerminalSymbol getTerminal(String identifier) {
        String strippedIdentifier = identifier.strip();
        if (strippedIdentifier.equals(epsilon)) {
            return TerminalSymbol.EPSILON;
        }
        return terminals.stream()
                .filter(t -> t.identifier.equals(strippedIdentifier))
                .findFirst()
                .orElseGet(() -> {
                    TerminalSymbol terminal = new TerminalSymbol(strippedIdentifier);
                    terminals.add(terminal);
                    return terminal;
                });
    }

    private boolean isStackSymbol(String identifier) {
        return stackSymbols.stream()
                .anyMatch(s -> s.identifier.equals(identifier));
    }

    private From parseFrom(String from) {
        String[] parts = from.split(",");

        State fromState = getState(parts[0]);

        if (parts.length == 3) { // From with terminal symbol
            TerminalSymbol terminalSymbol = getTerminal(parts[1]);
            StackSymbol stackSymbol = getStackSymbol(parts[2]);

            return new From(fromState, terminalSymbol, stackSymbol);
        } else { // Spontaneous transition
            StackSymbol stackSymbol = getStackSymbol(parts[1]);

            return new From(fromState, TerminalSymbol.EPSILON, stackSymbol);
        }
    }

    private To parseTo(String to) {
        String[] parts = to.split(",");

        State toState = getState(parts[0]);
        List<StackSymbol> stackSymbols = new ArrayList<>();
        String stackSymbolsString = parts[1].strip();

        for (int i = 0; i < stackSymbolsString.length(); i++) {
            stackSymbols.add(getStackSymbol(stackSymbolsString.charAt(i) + ""));
        }

        return new To(toState, stackSymbols.toArray(StackSymbol[]::new));
    }

    /**
     * Adds a function to the PDA.
     * The function must be in the form of "z, a, A -> q, AB | q, # ..." where z and q are states, a is a terminal symbol and A and B are stack symbols (defined in the constructor).
     *
     * @param function the function to be added
     */
    public void add(String function) {
        String[] parts = function.split("->");
        String[] tos = parts[1].split("\\|");

        From from = parseFrom(parts[0]);
        To[] to = Stream.of(tos)
                .map(this::parseTo)
                .toArray(To[]::new);

        pda.addFn(from, to);
    }

    public PDA getPDA() {
        return pda;
    }
}
