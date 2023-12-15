package symbols;

import java.util.List;

public record Word(Symbol... symbols) {

    public static Word empty() {
        return new Word();
    }


    public boolean isEmpty() {
        return symbols.length == 0;
    }

    public Symbol peek() {
        if (isEmpty()) return Symbol.defaultEpsilon();

        return symbols[symbols.length - 1];
    }


    public Word popAndClone() {
        if (isEmpty()) return new Word();

        Symbol[] clone = new Symbol[symbols.length - 1];

        System.arraycopy(symbols, 0, clone, 0, clone.length);

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
