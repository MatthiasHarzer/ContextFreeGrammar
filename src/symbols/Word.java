package symbols;

import java.util.List;

/**
 * Represents a word of a grammar.
 * @param symbols The symbols of the word.
 */
public record Word(Symbol... symbols) {

    public static Word empty() {
        return new Word();
    }

    public int length() {
        return symbols.length;
    }

    public boolean isEmpty() {
        return symbols.length == 0;
    }

    /**
     * Get the first symbol of the word (rtl)
     * @return The first symbol of the word
     */
    public Symbol first() {
        if (isEmpty()) return Symbol.defaultEpsilon();

        return symbols[0];
    }

    /**
     * Remove the first symbol from the word and return it (rtl)
     * @return A new word without the first symbol
     */
    public Word removeFirstAndClone() {
        if (isEmpty()) return new Word();

        Symbol[] clone = new Symbol[symbols.length - 1];

        System.arraycopy(symbols, 1, clone, 0, clone.length);

        return new Word(clone);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj.getClass() == this.getClass())) return false;
        Word s = (Word) obj;
        if (s.symbols.length != symbols.length) return false;
        for (int i = 0; i < symbols.length; i++) {
            if (!s.symbols[i].equals(symbols[i])) return false;
        }
        return true;
    }

    @Override
    public Word clone() {
        return new Word(List.of(symbols).toArray(Symbol[]::new));
    }
}
