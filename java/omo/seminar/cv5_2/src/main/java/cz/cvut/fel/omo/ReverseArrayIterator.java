package cz.cvut.fel.omo;

import java.util.NoSuchElementException;

public class ReverseArrayIterator {
     private int[] array;
     private int index;

    public ReverseArrayIterator(int[] array) {
        this.array = array;
        this.index = array.length - 1;
    }

    public int currentItem() {
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        throw new NoSuchElementException();
    }

    public int next() {
        index--;
        return currentItem();
    }

    public boolean isDone() {
        return index == 0;
    }

    public int first() {
        index = array.length - 1;
        return currentItem();
    }
}
