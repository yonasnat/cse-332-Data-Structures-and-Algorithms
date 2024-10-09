package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;
import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> arrangeHeap = new MinFourHeap<>(comparator);
        for (E a : array) {
            arrangeHeap.add(a);
        }

        int a = 0;
        while (arrangeHeap.hasWork()) {
            array[a] = arrangeHeap.next();
            a++;
        }

    }
}
