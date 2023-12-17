package pushdown_automaton;

import context_free_acceptor.ContextFreeAcceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * A minimal implementation of a <a href="https://en.wikipedia.org/wiki/Pushdown_automaton">Push Down Automaton (Kellerautomat)</a>.
 * Uses only Java's built-in data structures.
 * <p>
 * Requires for stack or alphabet symbols to be single characters.
 *
 * @see PDA
 *
 * @author Matthias Harzer
 */
public class MinimalPDA implements ContextFreeAcceptor {
    private final String stack;
    private final List<String[]> functions;
    private final String startState;
    private final List<String> alphabet;

    public MinimalPDA(String startState, String startStackSymbol) {
        this.stack = startStackSymbol;
        this.functions = new ArrayList<>();
        this.startState = startState;
        this.alphabet = new ArrayList<>();
    }

    public void addFn(
            String startState,
            String symbol,
            String stackSymbol,
            String endState,
            String endStackSymbols
    ) {

        functions.add(new String[]{startState, symbol, stackSymbol, endState, endStackSymbols});

        if (!alphabet.contains(symbol)) {
            alphabet.add(symbol);
        }
    }

    private List<String[]> advance(String[] configuration) {
        String state = configuration[0];
        String input = configuration[1];
        String stack = configuration[2];

        if (input.isEmpty() || stack.isEmpty()) {
            return new ArrayList<>();
        }

        String terminalSymbol = input.substring(0, 1);
        String stackSymbol = stack.substring(0, 1);

        String newStack = stack.substring(1);
        List<String[]> configurations = new ArrayList<>();

        for (String[] fn : functions) {
            if (fn[0].equals(state) && fn[1].equals(terminalSymbol) && fn[2].equals(stackSymbol)) {
                String endState = fn[3];
                String endStackSymbols = fn[4];

                configurations.add(new String[]{endState, input.substring(1), endStackSymbols + newStack});
            } else if (fn[0].equals(state) && fn[1].isEmpty() && fn[2].equals(stackSymbol)) {
                String endState = fn[3];
                String endStackSymbols = fn[4];

                configurations.add(new String[]{endState, input, endStackSymbols + newStack});
            }
        }

        return configurations;
    }

    public boolean accepts(String input) {
        String[] configuration = new String[]{this.startState, input, stack};
        List<String[]> configurations = new ArrayList<>();
        configurations.add(configuration);

        while (!configurations.isEmpty()) {
            List<String[]> newConfigurations = new ArrayList<>();

            for (String[] c : configurations) {
                List<String[]> newC = advance(c);
                newConfigurations.addAll(newC);
            }

            configurations = newConfigurations;

            for (String[] c : configurations) {
                if (c[1].isEmpty() && c[2].isEmpty()) {
                    return true;
                }
            }
        }


        return false;
    }

    public boolean accepts(symbols.Word word) {
        return accepts(word.toString());
    }

    @Override
    public grammar.Alphabet getAlphabet() {
        return new grammar.Alphabet(alphabet.toArray(String[]::new));
    }
}

