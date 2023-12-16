package context_free_acceptor;

import grammar.Alphabet;
import symbols.Word;

/**
 * A context-free acceptor can accept a {@link Word} over a {@link Alphabet} that is defined by a {@link grammar.ContextFreeGrammar}.
 *
 * @author Matthias Harzer
 */
public interface ContextFreeAcceptor {
    /**
     * Accepts a word over the alphabet of the acceptor.
     * @param input the word to be accepted
     * @return true if the word is accepted by the acceptor
     */
    boolean accepts(Word input);

    /**
     * Returns the alphabet of the acceptor.
     * @return the alphabet of the acceptor
     */
    Alphabet getAlphabet();

    /**
     * Accepts a word over the alphabet of the acceptor.
     * @param input the word to be accepted
     * @return true if the word is accepted by the acceptor
     */
    default boolean accepts(String input) {
        return accepts(getAlphabet().parse(input));
    }
}
