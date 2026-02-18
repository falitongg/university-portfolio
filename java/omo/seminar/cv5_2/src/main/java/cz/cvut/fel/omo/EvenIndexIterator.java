package cz.cvut.fel.omo;

import java.util.NoSuchElementException;

public class EvenIndexIterator implements Iterator {
    private int[] array;
    private int index;

    public EvenIndexIterator(int[] array) {
        this.array = array;
        index = 0;
    }

    @Override
    public int currentItem() {
        if (index >= 0 && index < array.length) {
            return array[index];
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int next() {
        index += 2;
        if (index >= array.length) {
            throw new NoSuchElementException();
        }
        return array[index];
    }

    @Override
    public boolean isDone() {
        return index + 2 >= array.length;
    }

    @Override
    public int first() {
        index = 0;
        return currentItem();
    }
}
