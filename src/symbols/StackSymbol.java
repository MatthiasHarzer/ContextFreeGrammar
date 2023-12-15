package symbols;

public class StackSymbol extends Symbol {
    public static final StackSymbol EPSILON = new StackSymbol("ε");
    public static final StackSymbol START_SYMBOL = new StackSymbol("#");

    public StackSymbol(String identifier) {
        super(identifier);
    }


    public boolean isEpsilon() {
        return this == EPSILON;
    }

    public boolean isStartSymbol() {
        return this == START_SYMBOL;
    }

}
