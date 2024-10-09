package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private final Comparator<E> comparator;
    @SuppressWarnings("unchecked")

    public MinFourHeap(Comparator<E> comparator) {
        this.data = (E[])new Object[10];
        this.comparator = comparator;
        this.size = 0;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        if (this.data.length == this.size) {
            this.grow();
        }
        this.data[this.size] = work;
        if (this.size > 0) {
            this.percolateUp(this.parent(this.size), this.size);
        }
        this.size++;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }

        E end = this.data[0];
        this.data[0] = this.data[this.size - 1];
        this.size--;
        if (this.size > 0) {
            int indexx = this.child(0,0);
            this.percolateDown(0, this.find(indexx));
        }
        return end;
    }

    @Override
    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        this.data = (E[])new Object[10];
        this.size = 0;
    }
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] arr = (E[])new Object[this.size * 2];
        int a = 0;
        while (a < this.size) {
            arr[a] = this.data[a];
            a++;
        }
        this.data = arr;
    }
    private int parent(int indexA) {
        return ((indexA - 1) / 4);
    }

    private int child(int childA, int parentA) {
        return ((parentA * 4) + childA + 1);
    }

    private void percolateUp(int parentA, int childA) {
        while (childA != 0 && this.comparator.compare(this.data[childA], this.data[parentA]) < 0) {
            E temp = this.data[childA];
            this.data[childA] = this.data[parentA];
            this.data[parentA] = temp;
            childA = parentA;
            parentA = this.parent(childA);
        }
    }

    private void percolateDown(int parentA, int childIndex) {
        while (childIndex > 0 && this.comparator.compare(this.data[parentA], this.data[childIndex]) > 0) {
            E temp = this.data[childIndex];
            this.data[childIndex] = this.data[parentA];
            this.data[parentA] = temp;
            parentA = childIndex;
            int first = this.child(0, parentA);
            childIndex = this.find(first);
        }
    }

    private int find(int startChild) {
        int low = startChild;
        int numb = startChild + 1;
        if (startChild > this.size) {
            return -1;
        } else {
            while (numb < startChild + 4){
                if (numb < this.size && this.comparator.compare(this.data[numb], this.data[low]) < 0) {
                    low = numb;
                }
                numb++;
            }

        }
        return low;
    }
}