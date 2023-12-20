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
    /**
     * A list of functions of the form <code>startState, symbol, stackSymbol, endState, endStackSymbols</code>.
     */
    private final List<String[]> functions;
    private final String startState;
    private boolean trim = true;

    public MinimalPDA(String startState, String startStackSymbol) {
        this.stack = startStackSymbol;
        this.functions = new ArrayList<>();
        this.startState = startState;
    }

    public MinimalPDA(String startState, String startStackSymbol, boolean trim) {
        this(startState, startStackSymbol);
        this.trim = trim;
    }

    public void addFn(
            String startState,
            String symbol,
            String stackSymbol,
            String endState,
            String endStackSymbols
    ) {
        functions.add(new String[]{startState, symbol, stackSymbol, endState, endStackSymbols});
    }

    private List<String[]> advance(String[] configuration) {
        String state = configuration[0];
        String input = configuration[1];
        String stack = configuration[2];

        if(trim){
            input = input.strip();
            stack = stack.strip();
        }

        if (input.isEmpty() || stack.isEmpty()) {
            return new ArrayList<>();
        }

//        if()




        List<String[]> configurations = new ArrayList<>();

        for (String[] fn : functions) {
            String fnStartState = fn[0];
            String fnStartSymbol = fn[1];
            String fnStartStackSymbol = fn[2];
            String fnEndState = fn[3];
            String fnEndStackSymbols = fn[4];

            if (!fnStartState.equals(state) || !stack.startsWith(fnStartStackSymbol)) continue;

            String newStack = stack.substring(fnStartStackSymbol.length());

            if (input.startsWith(fnStartSymbol)) { // normal transition
                configurations.add(new String[]{fnEndState, input.substring(fnStartSymbol.length()), fnEndStackSymbols + newStack});
            }
        }

        return configurations;
    }

    public boolean accepts(String input) {
        String[] configuration = new String[]{this.startState, input, stack};
        List<String[]> configurations = new ArrayList<>();
        configurations.add(configuration);
        long totalConfigurations = 1;

        while (!configurations.isEmpty()) {
            List<String[]> newConfigurations = new ArrayList<>();

            for (String[] c : configurations) {
                List<String[]> newC = advance(c);
                newConfigurations.addAll(newC);
            }

            configurations = newConfigurations
                    .stream().filter(c -> !c[0].isEmpty())
                    .toList();

            totalConfigurations += configurations.size();

            for (String[] c : configurations) {
                if (c[1].isEmpty() && c[2].isEmpty()) {
                    System.out.println("Total configurations: " + totalConfigurations);
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
        return new grammar.Alphabet(functions.stream()
                .map(fn -> fn[1])
                .toArray(String[]::new));
    }
}

