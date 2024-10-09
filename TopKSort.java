package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }


    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> arrangeHeap = new MinFourHeap<>(comparator);
        if (k <= 1) {
            throw new IndexOutOfBoundsException();
        }
        if (k > array.length) {
            k = array.length;
        }
        for (int i = 0; i < k; i++) {
            if (arrangeHeap.size() < k) {
                arrangeHeap.add(array[i]);
            }
        }
        for (int j = k; j < array.length; j++) {
            if (comparator.compare(array[j], arrangeHeap.peek()) > 0) {
                arrangeHeap.next();
                arrangeHeap.add(array[j]);
            }
            array[j] = null;
        }
        for (int i = 0; i < k; i++) {
            array[i] = arrangeHeap.next();
        }
    }
}
