package symbols;

/**
 * A symbol of a grammar.
 *
 * @see StackSymbol
 * @see TerminalSymbol
 * @see VariableSymbol
 * @see Epsilon
 *
 * @author Matthias Harzer
 */
public abstract class Symbol {
    /**
     * The default epsilon symbol.
     * @return The default epsilon symbol.
     */
    public static Epsilon defaultEpsilon() {
        return new Epsilon();
    }

    public final String identifier;

    public Symbol(String identifier) {
        this.identifier = identifier;
    }

    public TerminalSymbol asTerminal() {
        return new TerminalSymbol(identifier);
    }

    public VariableSymbol asVariable() {
        return new VariableSymbol(identifier);
    }

    public StackSymbol asStackSymbol() {
        return new StackSymbol(identifier);
    }

    public boolean isEpsilon() {
        return this == TerminalSymbol.EPSILON || this == StackSymbol.EPSILON || this instanceof Epsilon;
    }


    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o.getClass() == this.getClass())) return false;
        Symbol s = (Symbol) o;
        return s.identifier.equals(identifier);
    }
}
