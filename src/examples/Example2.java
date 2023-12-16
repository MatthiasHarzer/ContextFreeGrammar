package examples;

import parser.CFGParser;
import parser.PDAParser;

/**
 * This example accepts the same language as Example1, but uses the parser classes.
 *
 * The parser classes can be used to parse a string representation to a CFG or PDA.
 */
public class Example2 {

    public static void main(String[] args) {
        var cfgParser = new CFGParser("S"); // Define the start symbol and all variables of the grammar
        cfgParser.add("S -> aSa | bSb | a$a | b$b");
        var cfg3 = cfgParser.getGrammar();

        System.out.println("Testing CFG from CFGParser:");
        Example1.testCFA(cfg3);

        var pdaParser = new PDAParser("z0", "#", "A", "B"); // Define the start state, the start symbol of the stack and all stack symbols
        pdaParser.add("z0, a, # -> z1, #A");
        pdaParser.add("z0, b, # -> z1, #B");
        pdaParser.add("z1, a, # -> z1, #A");
        pdaParser.add("z1, b, # -> z1, #B");
        pdaParser.add("z1, $, # -> z1, ε");
        pdaParser.add("z1, a, A -> z1, ε");
        pdaParser.add("z1, b, B -> z1, ε");
        var pda3 = pdaParser.getPDA();

        System.out.println("----");
        System.out.println("Testing PDA from PDAParser:");
        Example1.testCFA(pda3);
    }
}
