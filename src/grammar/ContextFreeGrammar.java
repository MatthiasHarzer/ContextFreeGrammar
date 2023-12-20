package grammar;

import pushdown_automaton.PDA;
import symbols.TerminalSymbol;
import symbols.VariableSymbol;
import context_free_acceptor.ContextFreeAcceptor;
import symbols.Word;

import java.util.*;


/**
 * A context-free grammar is a 4-tuple (V, Σ, P, S) where V is a finite set of variables, Σ is a finite set of terminals,
 * P is a finite set of productions, and S is a start variable.
 *
 * @author Matthias Harzer
 */
public class ContextFreeGrammar implements ContextFreeAcceptor{
    private final Map<VariableSymbol, Production> productions;
    public final VariableSymbol start;

    public ContextFreeGrammar(VariableSymbol start) {
        this.productions = new HashMap<>();
        this.start = start;
    }

    private boolean hasProduction(Production production) {
        return productions.values().stream().anyMatch(p -> p.equals(production));
    }


    public List<VariableSymbol> getVariables() {
        return productions.keySet().stream().toList();
    }

    public Alphabet getAlphabet() {
        TerminalSymbol[] terminals = productions.values().stream()
                .flatMap(p -> Arrays.stream(p.getTerminals()))
                .distinct()
                .toArray(TerminalSymbol[]::new);

        return new Alphabet(terminals);
    }

    public Map<VariableSymbol, Production> getProductions() {
        return productions;
    }

    public Production addProduction(Production production) {
        if (!hasProduction(production)) productions.put(production.start, production);
        return production;
    }

    public boolean accepts(Word word) {
        if (getVariablesWithoutProduction().length > 0) {
            System.out.println("Warning: There are variables without a production!");
        }
        PDA pda = PDA.fromCFG(this);
        return pda.accepts(word);
    }

    /**
     * Returns all {@link VariableSymbol VariableSymbols} that do not have a production.
     */
    public VariableSymbol[] getVariablesWithoutProduction(){
        return productions.values().stream()
                .flatMap(p -> Arrays.stream(p.getVariables()))
                .distinct()
                .filter(v -> !productions.containsKey(v))
                .toArray(VariableSymbol[]::new);
    }

}
