package cz.cvut.fel.omo;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public class StandardArrayIterator implements Iterator {

    private int[] array;
    private int index;
    public StandardArrayIterator(int[] array) {
        this.array = array;
        this.index = 0;
    }

    public int currentItem() {
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        throw new NoSuchElementException();
    }

    @Override
    public int next() {
        index++;
        return currentItem();
    }

    @Override
    public boolean isDone() {
        return index == array.length - 1;
    }

    @Override
    public int first() {
        if (array.length == 0) {
            throw new NoSuchElementException();
        } else {
            index = 0;
            return currentItem();
        }
    }

}
