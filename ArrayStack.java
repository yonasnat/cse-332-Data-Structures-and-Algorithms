package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;
import java.util.NoSuchElementException;
/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    private E[] arr;
    private int size;

    public ArrayStack() {
        arr = (E[])new Object[10];
    }

    private E[] helpArray(int size) {
        return (E[])new Object[size];
    }

    @Override
    public void add(E work) {
        if(size == arr.length) {
            E[] copyArr = helpArray(arr.length * 2);
            for (int i = 0; i < arr.length; i++) {
                copyArr[i] = arr[i];
            }
            arr = copyArr;
        }
        arr[size] = work;
        size++;
    }


    @Override
    public E peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return arr[size - 1];
        }
    }


    @Override
    public E next() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E value = arr[--size];
        if (size == 0) {
            arr = (E[]) new Object[10];
        }
        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        arr = null;
    }

}
