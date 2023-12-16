package symbols;

/**
 * Represents a terminal symbol of a grammar.
 *
 * @author Matthias Harzer
 */
public class TerminalSymbol extends Symbol{
    /**
     * The TerminalSymbol representation of the epsilon symbol.
     */
    public static final TerminalSymbol EPSILON = new TerminalSymbol("<ε>");

    public TerminalSymbol(String identifier) {
        super(identifier);
    }

}
