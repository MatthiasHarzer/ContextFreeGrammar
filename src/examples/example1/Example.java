package examples.example1;

import context_free_acceptor.ContextFreeAcceptor;
import grammar.ContextFreeGrammar;
import grammar.Production;
import pushdown_automaton.PDA;
import pushdown_automaton.State;
import pushdown_automaton.functions.Function;
import symbols.StackSymbol;
import symbols.TerminalSymbol;
import symbols.VariableSymbol;

/**
 * This is a simple example of how to use Context Free Grammars or Pushdown Automata.
 * <br />
 * The language of this example should match the following:
 * Any word w ∈ {a, b}+ followed by a $ followed by the reverse of w.
 * For example: abb$bba, a$a, bab$bab, ...
 * <br /><br />
 *
 * The Context Free Grammar can easily be described by the following rules:
 * S -> aSa | bSb | a$a | b$b
 *<br /><br />
 *
 * The Pushdown Automaton can be described by the following rules:
 * <code>
 * z0, a, # -> z1, #A
 * z0, b, # -> z1, #B
 * z1, a, # -> z1, #A
 * z1, b, # -> z1, #B
 * z1, $, # -> z1, ε
 * z1, a, A -> z1, ε
 * z1, b, B -> z1, ε
 * </code>
 *
 */
public class Example {
    public static void main(String[] args) {
        // cfg() and pda() define the same language

        var pda = pda();
        var cfg = cfg();

        var pda2 = PDA.fromCFG(cfg); // this is how you can convert a CFG to a PDA

        System.out.println("Testing PDA:");
        testCFA(pda);
        System.out.println("----");
        System.out.println("Testing CFG:");
        testCFA(cfg);
        System.out.println("----");
        System.out.println("Testing PDA from CFG:");
        testCFA(pda2);
    }

    private static ContextFreeGrammar cfg() {
        var a = new TerminalSymbol("a");
        var b = new TerminalSymbol("b");
        var d = new TerminalSymbol("$");

        var S = new VariableSymbol("S");

        var grammar = new ContextFreeGrammar(S);

        grammar.addProduction(
                Production
                        .from(S)
                        .to(a, d, a)
                        .or(b, d, b)
                        .or(a, S, a)
                        .or(b, S, b)
        );
        return grammar;
    }


    private static PDA pda() {
        var a = new TerminalSymbol("a");
        var b = new TerminalSymbol("b");
        var d = new TerminalSymbol("$");

        var A = new StackSymbol("A");
        var B = new StackSymbol("B");

        var stackDefault = StackSymbol.START_SYMBOL;
        var epsilon = StackSymbol.EPSILON;

        var z0 = new State("z0");
        var z1 = new State("z1");


        var pda = new PDA(
                z0
        );

        pda.addFn(
                Function.from(z0, a, stackDefault)
                        .to(z1, stackDefault, A)
        );
        pda.addFn(
                Function.from(z0, b, stackDefault)
                        .to(z1, stackDefault, B)
        );
        pda.addFn(
                Function.from(z1, a, stackDefault)
                        .to(z1, stackDefault, A)
        );
        pda.addFn(
                Function.from(z1, b, stackDefault)
                        .to(z1, stackDefault, B)
        );
        pda.addFn(
                Function.from(z1, d, stackDefault)
                        .to(z1, epsilon)
        );
        pda.addFn(
                Function.from(z1, a, A)
                        .to(z1, epsilon)
        );
        pda.addFn(
                Function.from(z1, b, B)
                        .to(z1, epsilon)
        );

        return pda;
    }

    private static void testCFA(ContextFreeAcceptor cfa) {
        Object[][] tests = {
                {"a$a", true},
                {"abb$bba", true},
                {"ab$ab", false},
                {"ab$", false},
                {"abaabb$bbaaba", true}
        };

        for (Object[] test : tests) {
            String word = (String) test[0];
            boolean expected = (boolean) test[1];
            boolean actual = cfa.accepts(word);
            boolean success = expected == actual;
            System.out.println("[" + (success ? "PASSED" : "FAILED") + "] " + word + " -> " + actual + " (expected: " + expected + ")");
        }
    }
}
