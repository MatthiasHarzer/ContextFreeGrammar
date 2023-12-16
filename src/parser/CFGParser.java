package parser;

import grammar.ContextFreeGrammar;
import symbols.Symbol;
import symbols.TerminalSymbol;
import symbols.VariableSymbol;
import symbols.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A parser for {@link ContextFreeGrammar ContextFreeGrammars}. It is used to create a {@link ContextFreeGrammar} from a string representation.
 *
 * @author Matthias Harzer
 */
public class CFGParser {
    private final List<VariableSymbol> variables;
    private final List<TerminalSymbol> terminals;
    private final ContextFreeGrammar grammar;

    public CFGParser(String startSymbol, String... variables) {
        this.variables = new ArrayList<>();

        for (String variable : variables) {
            this.variables.add(getVariable(variable));
        }

        this.terminals = new ArrayList<>();

        VariableSymbol startSymbol1 = getVariable(startSymbol);
        this.grammar = new ContextFreeGrammar(startSymbol1);
    }

    private VariableSymbol getVariable(String identifier) {
        return variables.stream()
                .filter(v -> v.identifier.equals(identifier))
                .findFirst()
                .orElseGet(() -> {
                    VariableSymbol variable = new VariableSymbol(identifier);
                    variables.add(variable);
                    return variable;
                });
    }

    private TerminalSymbol getTerminal(String identifier) {
        return terminals.stream()
                .filter(t -> t.identifier.equals(identifier))
                .findFirst()
                .orElseGet(() -> {
                    TerminalSymbol terminal = new TerminalSymbol(identifier);
                    terminals.add(terminal);
                    return terminal;
                });
    }

    private boolean isVariable(String identifier) {
        return variables.stream()
                .anyMatch(v -> v.identifier.equals(identifier));
    }

    /**
     * Adds a production to the grammar.
     * The production must be in the form of "A -> aB | bC | a$a | b$b ..." where A, B and C are variables (defined in the constructor) and a, b, $ are terminals.
     * @param production the production to be added
     */
    public void add(String production) {
        String[] parts = production.split("->");
        VariableSymbol start = getVariable(parts[0].strip());
        String[] results = parts[1].split("\\|");

        List<Word> words = new ArrayList<>();

        for (String result : results) {
            String[] symbols = result.strip().split("");

            Symbol[] word = Arrays.stream(symbols)
                    .map(s -> {
                        if (isVariable(s)) {
                            return getVariable(s);
                        } else {
                            return getTerminal(s);
                        }
                    })
                    .toArray(Symbol[]::new);

            words.add(new Word(word));
        }

        this.grammar.addProduction(new grammar.Production(start, words.toArray(Word[]::new)));
    }

    public ContextFreeGrammar getGrammar() {
        return grammar;
    }
}
