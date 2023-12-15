package grammar;

import symbols.Symbol;
import symbols.Concat;
import symbols.VariableSymbol;

import java.util.HashSet;
import java.util.List;

public class Production {
    public final VariableSymbol start;
    public List<Concat> results;

    public Production(VariableSymbol start, Concat... results) {
        this.start = start;
        this.results = List.of(results);
    }

    public static Production from(VariableSymbol start) {
        return new Production(start, Concat.empty());
    }

    public Production to(Symbol... symbols) {
        return this.to(new Concat(symbols));
    }

    public Production to(Concat result) {
        this.results.add(result);
        return this;
    }

    public Production or(Concat result) {
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
