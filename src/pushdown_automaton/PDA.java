package pushdown_automaton;

import context_free_acceptor.ContextFreeAcceptor;
import grammar.Alphabet;
import grammar.ContextFreeGrammar;
import grammar.Production;
import pushdown_automaton.functions.From;
import pushdown_automaton.functions.Function;
import pushdown_automaton.functions.To;
import symbols.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A <a href="https://en.wikipedia.org/wiki/Pushdown_automaton">Push Down Automaton (Kellerautomat)</a> is a <a href="https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton">non-deterministic finite state machine</a> with a stack.
 * A PDA is able to accept a {@link ContextFreeGrammar}.
 *
 * @author Matthias Harzer
 */
public class PDA implements ContextFreeAcceptor {
    private final Stack stack;
    private final List<Function> functions;
    private final State initialState;

    public PDA(State initialState) {
        this(new ArrayList<>(), initialState);
    }

    public PDA(List<Function> functions, State initialState, Stack stack) {
        this.stack = stack;
        this.functions = functions;
        this.initialState = initialState;
    }

    public PDA(List<Function> functions, State initialState) {
        this(functions, initialState, new Stack());
        this.stack.add(StackSymbol.START_SYMBOL);
    }

    public List<State> getStates(){
        return functions.stream()
                .flatMap(fn -> Arrays.stream(fn.getStates()))
                .distinct()
                .toList();
    }

    public Alphabet getAlphabet(){
        TerminalSymbol[] terminals = functions.stream()
                .map(fn ->fn.start().terminalSymbol())
                .distinct()
                .toArray(TerminalSymbol[]::new);

        return new Alphabet(terminals);
    }

    private Function[] findMatchingFunctions(State state, Symbol terminalSymbol, Symbol stackSymbol) {
        return functions.stream()
                .filter(fn ->
                        fn.start().matches(state, terminalSymbol, stackSymbol)
                                || fn.start().matches(state, TerminalSymbol.EPSILON, stackSymbol) // Spontaneous transition
                )
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
        Word remainingInput = input;

        Function[] matchingFunctions = findMatchingFunctions(configuration.state(), terminalSymbol, stackSymbol);

        List<Configuration> configurations = new ArrayList<>();

        for (Function fn : matchingFunctions) {
            for (To result : fn.results()) {
                Stack newStack = new Stack(stack);
                if (!fn.start().terminalSymbol().equals(TerminalSymbol.EPSILON)) {
                    remainingInput = input.popAndClone();
                }
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

    /**
     * Converts a {@link ContextFreeGrammar} to a {@link PDA
     *
     * @param cfg
     * @return
     */
    public static PDA fromCFG(ContextFreeGrammar cfg) {
        State z = new State("z");
        PDA pda = new PDA(z);


        for (Production production : cfg.getProductions().values()) {
            boolean isStart = production.start.equals(cfg.start); // Is start symbol: S -> #
            StackSymbol A = isStart ? StackSymbol.START_SYMBOL : production.start.asStackSymbol();

            List<To> results = new ArrayList<>();

            for (Word result : production.results) {
                StackSymbol[] alpha = Arrays.stream(result.symbols())
                        // Make sure to replace the start symbol with the stack start symbol (S -> #)
                        .map(s -> s.equals(cfg.start) ? StackSymbol.START_SYMBOL : s.asStackSymbol())
                        .toArray(StackSymbol[]::new);

                if (alpha.length > 0) { // Don't add functions with epsilon transitions
                    results.add(new To(z, alpha));
                }

            }
            pda.addFn(
                    new From(z, TerminalSymbol.EPSILON, A),
                    results.toArray(new To[0])
            );
        }

        for (TerminalSymbol symbol : cfg.getAlphabet()) {
            StackSymbol a = symbol.asStackSymbol();
            pda.addFn(
                    new From(z, symbol, a),
                    new To(z, StackSymbol.EPSILON)
            );
        }


        return pda;
    }


}
