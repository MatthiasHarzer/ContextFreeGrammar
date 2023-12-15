package symbols;

public abstract class Symbol {
    public static Epsilon epsilon() {
        return new Epsilon();
    }


    public final String identifier;

    public Symbol(String identifier) {
        this.identifier = identifier;
    }

    public boolean isTerminal() {
        return this instanceof TerminalSymbol;
    }

    public boolean isVariable() {
        return this instanceof VariableSymbol;
    }

    public boolean isStackSymbol() {
        return this instanceof StackSymbol;
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
