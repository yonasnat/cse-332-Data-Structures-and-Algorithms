package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;
import java.util.NoSuchElementException;
/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    public E[] arr;
    private int size;
    private int front;
    private int back;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        arr = (E[])new Comparable[capacity];
    }


    @Override
    public void add(E work) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        arr[back] = work;
        back = (back + 1) % super.capacity();
        size++;
    }


    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        } else {
            return arr[front];
        }
    }

    @Override
    public E peek(int i) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        }else if(i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        return arr[(i + front) % capacity()];
    }


    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E end = arr[front];
        if (size == 1) {
            front = 0;
            back = 0;
        } else {
            front = (front + 1) % super.capacity();
        }
        size--;
        return end;
    }

    @Override
    public void update(int i, E value) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else if(i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        arr[(front + i) % super.capacity()] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = 0;
        back = 0;
        arr = (E[])new Comparable[this.capacity()];
    }


    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
