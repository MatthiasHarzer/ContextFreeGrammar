import grammar.Alphabet;
import grammar.ContextFreeGrammar;
import grammar.Production;
import pushdown_automaton.PDA;
import pushdown_automaton.State;
import pushdown_automaton.functions.*;
import symbols.StackSymbol;
import symbols.TerminalSymbol;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        var alphabet = new Alphabet("a", "b", "c", "e", "f", "g", "h", "i", "j", "$");
        var grammar = new ContextFreeGrammar(alphabet);

        var S = grammar.variable("S");
        var A = grammar.variable("A");
        var B = grammar.variable("B");

        var a = alphabet.getSymbol("a");
        var b = alphabet.getSymbol("b");
        var c = alphabet.getSymbol("c");
        var d = alphabet.getSymbol("$");

        grammar.addProduction(
                Production
                        .from(S)
                        .to(a, S, b)
                        .or(a, b)
        );

        pda();

    }

    private static void pda() {
        var a = new TerminalSymbol("a");
        var b = new TerminalSymbol("b");
        var d = new TerminalSymbol("$");
        var alphabet = new Alphabet(a, b, d);


        var A = new StackSymbol("A");
        var B = new StackSymbol("B");

        var stackDefault = StackSymbol.START_SYMBOL;
        var epsilon = StackSymbol.EPSILON;

        var z0 = new State("z0");
        var z1 = new State("z1");


        var pda = new PDA(
                List.of(z0, z1),
                z0
        );

        pda.addFn(
                new From(z0, a, stackDefault),
                new To(z1, stackDefault, A)
        );
        pda.addFn(
                new From(z0, b, stackDefault),
                new To(z1, stackDefault, B)
        );
        pda.addFn(
                new From(z1, a, stackDefault),
                new To(z1, stackDefault, A)
        );
        pda.addFn(
                new From(z1, b, stackDefault),
                new To(z1, stackDefault, B)
        );
        pda.addFn(
                new From(z1, d, stackDefault),
                new To(z1, epsilon)
        );
        pda.addFn(
                new From(z1, a, A),
                new To(z1, epsilon)
        );
        pda.addFn(
                new From(z1, b, B),
                new To(z1, epsilon)
        );

        var word = alphabet.parse("abb$bba");
        var word2 = alphabet.parse("abb$bbba");
        var word3 = alphabet.parse("aababb$bbabaa");

        System.out.println(pda.accepts(word));
        System.out.println(pda.accepts(word2));
        System.out.println(pda.accepts(word3));


    }
}
