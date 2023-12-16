package pushdown_automaton;

import symbols.StackSymbol;

/**
 * Represents the stack of a pushdown automaton.
 *
 * @author Matthias Harzer
 */
public class Stack extends java.util.Stack<StackSymbol>{
    public Stack() {
        super();
    }

    public Stack(Stack stack) {
        super();

        this.addAll(stack);
    }

    /**
     * Pushes an element onto the stack. If the element is an epsilon, it will not be pushed.
     * @param element element whose presence in this collection is to be ensured
     * @return true if the element was pushed
     */
    @Override
    public boolean add(StackSymbol element) {
        if(element.isEpsilon()) {
            return false;
        }

        return super.add(element);
    }

    /**
     * Pushes all elements of the given collection onto the stack in reverse order.
     * @param collection collection containing elements to be added to this collection
     * @return true if all elements were pushed
     */
    public boolean addAllReversed(java.util.Collection<? extends StackSymbol> collection) {
        java.util.Collection<StackSymbol> clone = new java.util.ArrayList<>(collection);
        java.util.Collections.reverse((java.util.List<?>) clone);
        return clone.stream().allMatch(this::add);
    }
}
