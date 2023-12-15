package grammar;

import symbols.Symbol;
import symbols.Word;
import symbols.VariableSymbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

    public Production or(Word result) {
        return this.to(result);
    }

    public Production or(Symbol... symbols) {
        return this.to(symbols);
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
