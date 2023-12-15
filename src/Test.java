import grammar.Alphabet;
import grammar.ContextFreeGrammar;
import grammar.Production;
import pushdown_automaton.PDA;
import pushdown_automaton.State;
import pushdown_automaton.functions.*;
import symbols.StackSymbol;

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
                        .to(a,S,b)
                        .or(a, b)
        );

//        var alphabet2 = new Alphabet()

//        var pda = new PDA()




    }

    private static void pda(){
        var alphabet = new Alphabet("a", "b", "$");
        var a = alphabet.getSymbol("a");
        var b = alphabet.getSymbol("b");
        var d = alphabet.getSymbol("$");

        var A = new StackSymbol("A");
        var B = new StackSymbol("B");

        var empt = StackSymbol.empty();

        var z0 = new State();
        var z1 = new State();

        var fn1 = new Function(
                new From(z0, a, empt),
                new To(z1, empt, A)
        );
        var fn2 = new Function(
                new From(z0, b, empt),
                new To(z1, empt, B)
        );
        var fn3 = new Function(
                new From(z1, a, empt),
                new To(z1, empt, A)
        );
        var fn4 = new Function(
                new From(z1, b, empt),
                new To(z1, empt, B)
        );

        var pda = new PDA(
                List.of(z0, z1),
                List.of(fn1, fn2, fn3, fn4)
        );


    }
}
