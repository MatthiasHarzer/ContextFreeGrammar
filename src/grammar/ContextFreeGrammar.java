package grammar;

import symbols.Concat;
import symbols.VariableSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextFreeGrammar {
    private final List<VariableSymbol> variables;
    private final Alphabet alphabet;
    private final Map<VariableSymbol, Production> productions;

    public ContextFreeGrammar(Alphabet alphabet) {
        this.variables = new ArrayList<>();
        this.productions = new HashMap<>();
        this.alphabet = alphabet;
    }

    private boolean hasProduction(Production production) {
        return productions.values().stream().anyMatch(p -> p.equals(production));
    }

    private VariableSymbol getVariable(String identifier){
        return variables.stream().filter(v -> v.identifier.equals(identifier)).findFirst().orElse(null);
    }

    private boolean hasVariable(String identifier) {
        return variables.stream().anyMatch(v -> v.identifier.equals(identifier));
    }

    public VariableSymbol variable(String identifier)  {
        VariableSymbol existing = getVariable(identifier);
        if (existing != null) return existing;

        VariableSymbol v = new VariableSymbol(identifier);
        variables.add(v);

        return v;
    }

    public Production addProduction(Production production) {
        if (!hasProduction(production)) productions.put(production.start, production);
        return production;
    }

    public boolean accepts(Concat word){

    }

}
