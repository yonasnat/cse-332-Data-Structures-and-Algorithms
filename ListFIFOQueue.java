package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private int size;
    private ListNode front;
    private ListNode back;

    private class ListNode {
       E data;
       ListNode next;

       public ListNode(E work) {
           this(work, null);
       }

       public ListNode(E work, ListNode next) {
           this.data = work;
           this.next = next;
       }
    }


    public ListFIFOQueue() {
        size = 0;
        front = null;
        back = null;
    }

    @Override
    public void add(E work) {
        size++;
        if (front != null) {
            back.next = new ListNode(work);
            back = back.next;
        } else {
            front = new ListNode(work);
            back = front;
        }
    }

    @Override
    public E peek() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        return front.data;
    }

    @Override
    public E next() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        E value = front.data;
        if (front == back) {
            back = null;
        }
        front = front.next;
        size--;
        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = null;
        back = null;
    }
}
