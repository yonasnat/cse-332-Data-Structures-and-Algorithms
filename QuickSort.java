package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quick(array, comparator, 0, array.length);
    }

    private static <E> void quick(E[] array, Comparator<E> comparator, int start, int end) {
        int top = 2;
        if (end - start >= top) {
            E hold = array[end - 1];
            int split = start - 1;
            for (int i = start; i < end; i++) {
                if (comparator.compare(array[i], hold) <= 0) {
                    split++;
                    swap(array, split, i);
                }
            }
            quick(array, comparator, start, split);
            quick(array, comparator, split + 1, end);
        }
    }
    private static <E> void swap(E[] array, int one, int two) {
        E curr = array[one];
        array[one] = array[two];
        array[two] = curr;
    }
}
