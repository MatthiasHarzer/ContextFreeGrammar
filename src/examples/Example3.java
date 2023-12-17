package examples;

import parser.CFGParser;


/**
 * This example accepts the language <code>{a^m b^(m+n) c^n | n >= 1}</code>.
 */
public class Example3 {
    public static void main(String[] args) {
        var cfgParser = new CFGParser("S", "A", "B");
        cfgParser.add("S -> AB");
        cfgParser.add("A -> aAb | ab");
        cfgParser.add("B -> bBc | bc");

        var cfg = cfgParser.getGrammar();

        System.out.println("Testing CFG from CFGParser:");
        TestCFA.testCFA(cfg,
                new TestCFA("abbc", true),
                new TestCFA("aabbbc", true),
                new TestCFA("aabcc", false)
        );
    }
}
