package symbols;

/**
 * Represent a StackSymbol of a pushdown automator.
 *
 * @see pushdown_automaton.PDA
 * @author Matthias Harzer
 */
public class StackSymbol extends Symbol {
    /**
     * The StackSymbol representation of the epsilon symbol.
     */
    public static final StackSymbol EPSILON = new StackSymbol("<ε>");
    /**
     * The start symbol of the pushdown automaton.
     */
    public static final StackSymbol START_SYMBOL = new StackSymbol("<#>");

    public StackSymbol(String identifier) {
        super(identifier);
    }

    public boolean isStartSymbol() {
        return this == START_SYMBOL;
    }

}
