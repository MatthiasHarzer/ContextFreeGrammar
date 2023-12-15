package pushdown_automaton;

import symbols.StackSymbol;

public class Stack extends java.util.Stack<StackSymbol>{
    public Stack() {
        super();
    }

    public Stack(Stack stack) {
        super();

        this.addAll(stack);
    }

    @Override
    public boolean add(StackSymbol element) {
        if(element.isEpsilon()) {
            return false;
        }

        return super.add(element);
    }

    public boolean addAllReversed(java.util.Collection<? extends StackSymbol> collection) {
        java.util.Collection<StackSymbol> clone = new java.util.ArrayList<>(collection);
        java.util.Collections.reverse((java.util.List<?>) clone);
        return clone.stream().allMatch(this::add);
    }
}
