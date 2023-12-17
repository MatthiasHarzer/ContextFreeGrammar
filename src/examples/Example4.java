package examples;

import pushdown_automaton.MinimalPDA;

public class Example4 {
    public static void main(String[] args) {
        var pda = new MinimalPDA("z0", "#");
        pda.addFn("z0", "a", "#", "z1", "#A");
        pda.addFn("z0", "b", "#", "z1", "#B");
        pda.addFn("z1", "a", "#", "z1", "#A");
        pda.addFn("z1", "b", "#", "z1", "#B");
        pda.addFn("z1", "$", "#", "z1", "");
        pda.addFn("z1", "a", "A", "z1", "");
        pda.addFn("z1", "b", "B", "z1", "");

        System.out.println("Testing PDA from MinimalPDA:");
        Example1.testCFA(pda);
    }
}
