package cz.cvut.fel.omo;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class ExtendedStackImpl implements ExtendedStack {

    private Stack stack;
    public ExtendedStackImpl() {
        stack = new StackImpl();
    }

    @Override
    public void push(int toInsert) {
        stack.push(toInsert);
    }

    @Override
    public void push(int[] toInsert) {
        for (int value : toInsert){
            stack.push(value);
        }
    }

    @Override
    public int top() {
        if (stack.isEmpty()){
            throw new EmptyStackException();
        }
        int popped = stack.pop();
        stack.push(popped);
        return popped;
    }

    @Override
    public int pop() {
        return  stack.pop();
    }

    @Override
    public int popFirstNegativeElement() {
        Stack tempStack = new StackImpl();
        int negativeValue = 0;
        boolean found = false;

        if (stack.isEmpty()){
            throw new EmptyStackException();
        }
        while (!stack.isEmpty()){
            int value = stack.pop();
            if (value < 0){
                negativeValue = value;
                found = true;
                break;
            } else {
                tempStack.push(value);
            }
        }
        while (!tempStack.isEmpty()){
            int value = tempStack.pop();
            stack.push(value);
        }

        if (!found) {
            throw new NoSuchElementException("No negative element found");
        }

        return negativeValue;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
