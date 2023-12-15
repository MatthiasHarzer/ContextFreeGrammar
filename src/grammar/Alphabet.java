package grammar;

import symbols.TerminalSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alphabet {
    public final List<TerminalSymbol> symbols;

    public Alphabet(List<TerminalSymbol> symbols) {
        this.symbols = symbols;
    }

    public boolean hasSymbol(String identifier) {
        return symbols.stream().anyMatch(s -> s.identifier.equals(identifier));
    }

    public TerminalSymbol getSymbol(String identifier) {
        return symbols.stream().filter(s -> s.identifier.equals(identifier)).findFirst().orElse(null);
    }

    public Alphabet() {
        this.symbols = new ArrayList<>();
    }

    public Alphabet(TerminalSymbol... symbols) {
        this.symbols = new ArrayList<>();
        this.symbols.addAll(Arrays.asList(symbols));
    }

    public Alphabet(String... symbols) {
        this.symbols = new ArrayList<>();
        for (String s : symbols) {
            this.symbols.add(new TerminalSymbol(s));
        }
    }

    public boolean add(TerminalSymbol symbol) {
        if (hasSymbol(symbol.identifier)) return false;
        return symbols.add(symbol);
    }

    public boolean add(String identifier) {
        if (hasSymbol(identifier)) return false;
        TerminalSymbol symbol = new TerminalSymbol(identifier);

        return symbols.add(symbol);
    }

}
