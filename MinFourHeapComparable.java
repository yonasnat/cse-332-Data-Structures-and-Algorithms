package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        this.size = 0;
        this.data = (E[]) new Comparable[10];
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work) {
        if (size == data.length) {
            resize();
        }

        data[size] = work;
        percolateUp(size);
        size++;
    }

    private void resize() {
        E[] newData = (E[]) new Comparable[data.length * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E min = data[0];
        data[0] = data[size - 1];
        size--;
        percolateDown(0);
        return min;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        data = (E[]) new Comparable[10];
    }

    private void percolateUp(int index) {
        while (index > 0 && data[index].compareTo(data[parent(index)]) < 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }
    private int parent(int index) {
        return (index - 1) / 4;
    }
    private int childIndex(int index, int child) {
        return 4 * index + child;
    }
    private void swap(int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void percolateDown(int index) {
        int smallest = index;
        for (int i = 1; i <= 4; i++) {
            int child = childIndex(index, i);
            if (child < size && data[child].compareTo(data[smallest]) < 0) {
                smallest = child;
            }
        }
        if (smallest != index) {
            swap(index, smallest);
            percolateDown(smallest);
        }
    }
}


