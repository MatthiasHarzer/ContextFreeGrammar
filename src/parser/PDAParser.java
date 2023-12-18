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
 * Note: <code>ε</code> can be used as the epsilon symbol throughout the parser. Use {@link #setEpsilon(String)} to change it.
 *
 * @author Matthias Harzer
 */
public class PDAParser {
    private String epsilon = "ε";
    private String separator = ",";
    private String transitionSeparator = "->";
    private String resultSeparator = "|";
    private final String stackStartSymbol;
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
     *
     * @param epsilon the string that is used as the epsilon symbol
     */
    public void setEpsilon(String epsilon) {
        this.epsilon = epsilon;
    }

    /**
     * Set the string that is used as the separator for states, stack symbols and terminal symbols (default ,).
     *
     * @param separator the string to use as the separator
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Set the string that is used to indicate a transition (default ->).
     *
     * @param transitionSeparator the string that is used to indicate a transition
     */
    public void setTransitionSeparator(String transitionSeparator) {
        this.transitionSeparator = transitionSeparator;
    }

    /**
     * Set the string that is used to separate multiple results of a transition (default |), indicating multiple results of the same transition.
     *
     * @param resultSeparator the string that is used to separate the results of a transition
     */
    public void setResultSeparator(String resultSeparator) {
        this.resultSeparator = resultSeparator;
    }

    /**
     * Configures the PDA with the epsilon symbol and separators. If null is passed, the value will be ignored.
     *
     * @param epsilon             the string that is used as the epsilon symbol (empty word)
     * @param separator           the string to use as the separator
     * @param transitionSeparator the string that is used to indicate a transition
     * @param resultSeparator     the string that is used to separate the results of a transition
     * @return this parser
     */
    public PDAParser configured(String epsilon, String separator, String transitionSeparator, String resultSeparator) {
        if (this.epsilon != null) this.epsilon = epsilon;
        if (this.separator != null) this.separator = separator;
        if (this.transitionSeparator != null) this.transitionSeparator = transitionSeparator;
        if (this.resultSeparator != null) this.resultSeparator = resultSeparator;
        return this;
    }

    private StackSymbol getStackSymbol(String identifier) {
        String strippedIdentifier = identifier.strip();
        if (strippedIdentifier.equals(epsilon) || strippedIdentifier.isEmpty()) {
            return StackSymbol.EPSILON;
        }
        if (strippedIdentifier.equals(stackStartSymbol)) {
            return StackSymbol.START_SYMBOL;
        }
        StackSymbol symbol = stackSymbols.stream()
                .filter(s -> s.identifier.equals(strippedIdentifier))
                .findFirst()
                .orElse(null);

        if (symbol == null) {
            throw new IllegalArgumentException("Stack symbol '%s' is not defined in %s".formatted(strippedIdentifier, stackSymbols));
        }

        return symbol;
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
        String[] parts = ParserUtil.split(from, separator);


        State fromState = getState(parts[0]);

        if (parts.length == 3) { // From with terminal symbol
            TerminalSymbol terminalSymbol = getTerminal(parts[1]);
            StackSymbol stackSymbol = getStackSymbol(parts[2]);

            return new From(fromState, terminalSymbol, stackSymbol);
        } else { // Spontaneous transition
            if (parts.length != 2)
                throw new IllegalArgumentException("From '%s' is invalid. Expected format: 'q%s a%s A' or 'q%s A'".formatted(from, separator, separator, separator));
            StackSymbol stackSymbol = getStackSymbol(parts[1]);

            return new From(fromState, TerminalSymbol.EPSILON, stackSymbol);
        }
    }

    private To parseTo(String to) {
        to = to.strip();
        String[] parts = ParserUtil.split(to, separator);

        if (parts.length != 2)
            throw new IllegalArgumentException("Result '%s' is invalid. Expected format: 'q%s AB'".formatted(to, separator));

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
        String[] parts = ParserUtil.split(function, transitionSeparator);

        if (parts.length != 2)
            throw new IllegalArgumentException("Function '%s' must have exactly one transition operator: %s".formatted(function, transitionSeparator));

        String[] tos = ParserUtil.split(parts[1], resultSeparator);

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
