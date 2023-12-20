package grammar;

import symbols.Symbol;
import symbols.TerminalSymbol;
import symbols.Word;
import symbols.VariableSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * A production is a rule of the form A -> α, where A is a {@link VariableSymbol} and α is a {@link Word}.
 *
 * @see ContextFreeGrammar
 * @author Matthias Harzer
 */
public class Production {
    public final VariableSymbol start;
    public List<Word> results;

    public Production(VariableSymbol start, Word... results) {
        this.start = start;
        this.results = new ArrayList<>();
        this.results.addAll(List.of(results));
    }

    public static Production from(VariableSymbol start) {
        return new Production(start, Word.empty());
    }

    public Production to(Symbol... symbols) {
        return this.to(new Word(symbols));
    }

    public Production to(Word result) {
        this.results.add(result);
        return this;
    }

    public Production to(Alphabet alphabet){
        for(TerminalSymbol s : alphabet.getSymbols()){
            this.to(s);
        }
        return this;
    }

    public Production or(Word result) {
        return this.to(result);
    }

    public Production or(Symbol... symbols) {
        return this.to(symbols);
    }

    public Production or(Alphabet alphabet){
        return this.to(alphabet.getSymbols());
    }

    /**
     * Returns all {@link TerminalSymbol TerminalSymbols} that are involved in the production.
     */
    public TerminalSymbol[] getTerminals() {
        return results.stream()
                .flatMap(w -> Arrays.stream(w.symbols()))
                .filter(s -> s instanceof TerminalSymbol)
                .map(s -> (TerminalSymbol) s)
                .toArray(TerminalSymbol[]::new);
    }

    public VariableSymbol[] getVariables(){
        return results.stream()
                .flatMap(w -> Arrays.stream(w.symbols()))
                .filter(s -> s instanceof VariableSymbol)
                .map(s -> (VariableSymbol) s)
                .toArray(VariableSymbol[]::new);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj.getClass() == this.getClass())) return false;
        Production p = (Production) obj;
        return p.start.equals(start)
                && new HashSet<>(p.results).equals(new HashSet<>(results));
    }


}
