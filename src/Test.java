import context_free_acceptor.ContextFreeAcceptor;
import grammar.ContextFreeGrammar;
import grammar.Production;
import pushdown_automaton.PDA;
import pushdown_automaton.State;
import pushdown_automaton.functions.Function;
import symbols.StackSymbol;
import symbols.TerminalSymbol;
import symbols.VariableSymbol;


public class Test {
    public static void main(String[] args) {

        var pda = pda();

        var cfg = cfg();
        var pda2 = PDA.fromCFG(cfg);

        testCFA(pda);
        System.out.println("----");
        testCFA(cfg);
        System.out.println("----");
        testCFA(pda2);

    }

    private static void testCFA(ContextFreeAcceptor cfa) {
        var alphabet = cfa.getAlphabet();
        var word = alphabet.parse("abb$bba");
        var word2 = alphabet.parse("abb$bbba");
        var word3 = alphabet.parse("aababb$bbabaa");

        System.out.println(cfa.accepts(word)); // true
        System.out.println(cfa.accepts(word2)); // false
        System.out.println(cfa.accepts(word3)); // true
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
                        .to(z0, stackDefault, A)
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
}
