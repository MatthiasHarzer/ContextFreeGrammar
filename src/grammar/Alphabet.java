package grammar;

import symbols.Symbol;
import symbols.TerminalSymbol;
import symbols.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * An alphabet is a finite set of {@link TerminalSymbol TerminalSymbols}.
 *
 * @author Matthias Harzer
 */
public class Alphabet implements Iterable<TerminalSymbol> {
    private final List<TerminalSymbol> symbols;

    public Alphabet(List<TerminalSymbol> symbols) {
        this.symbols = symbols;
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

    public boolean hasSymbol(String identifier) {
        return symbols.stream().anyMatch(s -> s.identifier.equals(identifier));
    }

    public TerminalSymbol getSymbol(String identifier) {
        return symbols.stream().filter(s -> s.identifier.equals(identifier)).findFirst().orElse(null);
    }

    public boolean add(TerminalSymbol... symbol) {
        return symbols.addAll(Arrays.asList(symbol));
    }

    public boolean add(String identifier) {
        if (hasSymbol(identifier)) return false;
        TerminalSymbol symbol = new TerminalSymbol(identifier);

        return symbols.add(symbol);
    }

    /**
     * Parses a string into a {@link Word}. If the string contains a symbol that is not in the alphabet, null is returned.
     *
     * @param input the string to parse
     * @return the parsed word
     */
    public Word parse(String input) {
        Symbol[] word = new Symbol[input.length()];
        for (int i = input.length() - 1; i >= 0; i--) {
            TerminalSymbol symbol = getSymbol(input.charAt(i) + "");
            if (symbol == null) return null;
            word[i] = symbol;
        }
        return new Word(word);
    }

    @Override
    public Iterator<TerminalSymbol> iterator() {
        return symbols.iterator();
    }
}
