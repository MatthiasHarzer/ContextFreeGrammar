package pushdown_automaton;

import context_free_acceptor.ContextFreeAcceptor;

import java.util.*;

public class MinimalPDA implements ContextFreeAcceptor {
    private final String stack;
    private final Map<String, List<String[]>> functions;
    private final String startState;
    private final List<String> alphabet;

    public MinimalPDA(String startState, String startStackSymbol) {
        this.stack = startStackSymbol;
        this.functions = new HashMap<>();
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
        String fnKey = startState + symbol + stackSymbol;
        List<String[]> fnValue = functions.get(fnKey);
        String[] endArray = {endState, endStackSymbols};
        if (fnValue == null) {
            List<String[]> l = new ArrayList<>();
            l.add(endArray);
            functions.put(fnKey, l);
        } else {
            fnValue.add(endArray);
        }

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

        String fnKey = state + terminalSymbol + stackSymbol;
        List<String[]> fnValue = functions.get(fnKey);

        if (fnValue == null) {
            return new ArrayList<>();
        }

        List<String[]> configurations = new ArrayList<>();
        String newStack = stack.substring(1);

        for (String[] fnResult : fnValue){
            String newState = fnResult[0];
            String newStackSymbols = fnResult[1];

            String remainingInput = input.substring(1);

            newStack = newStackSymbols + newStack;

            configurations.add(new String[]{newState, remainingInput, newStack});
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

