package symbols;

public class StackSymbol extends Symbol {

    public StackSymbol(String identifier) {
        super(identifier);
    }

    public static StackSymbol empty(){
        return new StackSymbol("");
    }
}
