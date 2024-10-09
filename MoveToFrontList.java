package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private ListNode front;

    private class ListNode {
        private Item<K, V> data;
        private ListNode next;

        public ListNode(Item<K, V> item) {
            this(item, null);
        }

        public ListNode(Item<K, V> item, ListNode next) {
            this.data = item;
            this.next = next;
        }
    }

    public MoveToFrontList() {
        this(null);
    }


    public MoveToFrontList(Item<K, V> stuff) {
        this.front = new ListNode(stuff);
        if (stuff == null || stuff.key == null || stuff.value == null) {
            this.size = 0;
        } else {
            this.size = 1;
        }
    }



    @Override
    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            throw new IllegalArgumentException();
        }
        ListNode news = new ListNode(new Item<>(key, value), this.front);
        V start = find(key);
        if (start == null) {
            this.size++;
            this.front = news;
            return null;
        }
        this.front.data.value = value;
        return start;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == 0) {
            return null;
        }
        ListNode now = this.front;
        V end = null;
        if (now != null && now.data != null) {
            if (now.data.key.equals(key)) {
                return this.front.data.value;
            }
            while (now.next.data != null && now.next != null && !now.next.data.key.equals(key)) {
                now = now.next;
            }
            if (now.next.data != null && now.next != null) {
                end = now.next.data.value;
                ListNode found = now.next;
                now.next = found.next;
                found.next = this.front;
                this.front = found;
            }
        }
        return end;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFLIterator();
    }

    private class MTFLIterator extends SimpleIterator<Item<K, V>> {
        private ListNode present;

        public MTFLIterator() {
            this.present = MoveToFrontList.this.front;
        }

        public boolean hasNext() {
            return present != null && present.next != null;
        }

        public Item<K, V> next() {
            Item<K, V> valueRet = present.data;
            present = present.next;
            return valueRet;
        }

    }

}
