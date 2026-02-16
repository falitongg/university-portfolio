package cz.cvut.fel.pjv.impl;

import cz.cvut.fel.pjv.Queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the {@link Queue} backed by fixed size array.
 */
public class CircularArrayQueue implements Queue {
    private final String[] data;
    private int head;
    private int end;
    private int size;
    private final int capacity;

    public CircularArrayQueue() {
        this(5);
    }

    /**
     * Creates the queue with given {@code capacity}. The capacity represents maximal number of elements that the
     * queue is able to store.
     * @param capacity of the queue
     */
    public CircularArrayQueue(int capacity) {
        this.capacity = capacity;
        this.data = new String[capacity];
        this.head = 0;
        this.end = 0;
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull() {
        if (size == capacity){
            return true;
        }
        return false;
    }

    @Override
    public boolean enqueue(String obj) {
        if(isFull() || obj == null){
            return false;
        }
        data[end] = obj;
        end = (end + 1)%capacity;
        size++;
        return true;
    }

    @Override
    public String dequeue() {
        if(isEmpty()){
            return null;
        }
        String element = data[head];
        head = (head + 1)%capacity;
        size--;
        return element;
    }

    @Override
    public Collection<String> getElements() {
        List<String> elements = new ArrayList<>();
        int index = head;
        for (int i = 0; i < size; i++) {
            if (data[index] != null){
                elements.add(data[index]);
            }
            index = (index + 1)%capacity;
        }
        return elements;
    }

    @Override
    public void printAllElements() {
        int index = head;
        for (int i = 0; i < size; i++) {
            if (data[index] != null) {
                System.out.println(data[index]);
            }
            index = (index + 1) % capacity;
        }
    }
}
