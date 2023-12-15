package pushdown_automaton;

import pushdown_automaton.functions.From;
import pushdown_automaton.functions.Function;
import pushdown_automaton.functions.To;
import symbols.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PDA {
    private final Stack stack;
    private final List<State> states;
    private final List<Function> functions;
    private final State initialState;

    public PDA(List<State> state, State initialState) {
        this(state, new ArrayList<>(), initialState);
    }

    public PDA(List<State> states, List<Function> functions, State initialState, Stack stack) {
        this.stack = stack;
        this.states = states;
        this.functions = functions;
        this.initialState = initialState;
    }

    public PDA(List<State> states, List<Function> functions, State initialState) {
        this(states, functions, initialState, new Stack());
        this.stack.add(StackSymbol.START_SYMBOL);
    }

    private Function[] findMatchingFunctions(State state, Symbol terminalSymbol, Symbol stackSymbol) {
        return functions.stream()
                .filter(fn -> fn.start().matches(state, terminalSymbol, stackSymbol))
                .toArray(Function[]::new);
    }

    public void addFn(Function fn) {
        functions.add(fn);
    }

    public Function addFn(From start, To... results) {
        Function fn = new Function(start, results);
        addFn(fn);
        return fn;
    }

    /**
     * Gets all possible mutation configurations of the PDA depending on a given configuration
     *
     * @param configuration The current configuration of the PDA
     * @return An array of PDA configurations
     */
    private Configuration[] advance(Configuration configuration) {
        Word input = configuration.input();
        Stack stack = configuration.stack();


        Symbol terminalSymbol = input.peek();
        Symbol stackSymbol = stack.peek();
        Word remainingInput = input.popAndClone();

        Function[] matchingFunctions = findMatchingFunctions(configuration.state(), terminalSymbol, stackSymbol);

        List<Configuration> configurations = new ArrayList<>();

        for (Function fn : matchingFunctions) {
            for (To result : fn.results()) {
                Stack newStack = new Stack(stack);
                newStack.pop();

                newStack.addAllReversed(List.of(result.stackSymbols()));

                configurations.add(new Configuration(result.state(), remainingInput, newStack));
            }
        }

        return configurations.toArray(new Configuration[0]);
    }


    /**
     * Checks if the PDA accepts a given word
     *
     * @param word The word to check
     * @return Whether the PDA accepts the word
     */
    public boolean accepts(Word word) {
        Configuration[] configurations = new Configuration[]{new Configuration(initialState, word, stack)};

        while (configurations.length > 0) {
            // Get all possible configurations from the current configurations
            configurations = Arrays.stream(configurations)
                    .map(this::advance)
                    .flatMap(Arrays::stream)
                    .toArray(Configuration[]::new);

            // Check if any of the configurations are in an accepting state
            boolean anyEmpty = Arrays.stream(configurations)
                    .anyMatch(Configuration::accepted);

            if (anyEmpty) return true;

            configurations = Arrays.stream(configurations)
                    .filter(Configuration::valid)
                    .toArray(Configuration[]::new);
        }

        return false;
    }


}
