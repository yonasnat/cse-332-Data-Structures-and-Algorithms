package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V>{
    static final int[] PRIME_SIZES = {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};
    private Supplier<Dictionary<K, V>> newBucket;
    private int elementsCount;
    private Dictionary<K, V>[] storageArray;
    private double maxLoadFactor = 0.75;


@SuppressWarnings("unchecked")
public ChainingHashTable(Supplier<Dictionary<K, V>> newBucket) {
    this.newBucket = newBucket;
    this.size = 0;
    this.elementsCount = 0;
    this.storageArray = (Dictionary<K, V>[]) new Dictionary[PRIME_SIZES[0]];
    if (this.storageArray.length <= 1) {
        throw new ArrayIndexOutOfBoundsException();
    }
    for (Dictionary<K, V> store : this.storageArray) {
        store = newBucket.get();
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
    if ((this.size() / (double) this.storageArray.length) >= maxLoadFactor) {
        doHash();
    }
    Dictionary<K, V> temp = findBucket(key);
    V oldValue = temp.insert(key, value);
    if (oldValue == null) {
        this.elementsCount++;
    }
    return oldValue;
}

@Override
public V find(K key) {
    if (key == null) {
        throw new IllegalArgumentException();
    }
    Dictionary<K, V> temp = findBucket(key);
    return temp.find(key);
}

@Override
public Iterator<Item<K, V>> iterator() {
    if (this.storageArray[0] == null) {
        this.storageArray[0] = newBucket.get();
    }
    Iterator<Item<K, V>> itemIterator = new Iterator<>() {
        int currentIndex = 0;
        Iterator<Item<K, V>> currentIterator = storageArray[0].iterator();

        @Override
        public boolean hasNext() {
            if (currentIterator.hasNext()) {
                return currentIterator.hasNext();
            }
            currentIndex++;
            while (currentIndex < storageArray.length && storageArray[currentIndex] == null) {
                currentIndex++;
            }
            if (currentIndex >= storageArray.length) {
                return false;
            }
            currentIterator = storageArray[currentIndex].iterator();
            return currentIterator.hasNext();
        }

        @Override
        public Item<K, V> next() {
            if (currentIterator.hasNext()) {
                return currentIterator.next();
            }
            currentIndex++;
            while (currentIndex < storageArray.length && (storageArray[currentIndex] == null || !storageArray[currentIndex].iterator().hasNext())) {
                currentIndex++;
            }
            if (currentIndex >= storageArray.length) {
                return null;
            }
            currentIterator = storageArray[currentIndex].iterator();
            return currentIterator.next();
        }
    };
    return itemIterator;
}

@SuppressWarnings("unchecked")
private void doHash() {
    int newSize = (15 * (this.storageArray.length - 1) / 6);
    if (this.elementsCount < PRIME_SIZES.length - 1) {
        this.elementsCount++;
        newSize = PRIME_SIZES[this.elementsCount];
    }
    Dictionary<K, V>[] others = this.storageArray;
    this.storageArray = new Dictionary[newSize];
    this.size = 0;
    for (Dictionary<K, V> currentBucket : others) {
        if (currentBucket != null) {
            Iterator<Item<K, V>> itr = currentBucket.iterator();
            while (itr.hasNext()) {
                Item<K, V> item = itr.next();
                this.insert(item.key, item.value);
            }
        }
    }
}

public int size() {
    return this.size;
}

private int calculateHash(K key) {
    if (key == null) {
        throw new IllegalArgumentException();
    }
    int hashValue = Math.abs(key.hashCode());
    hashValue %= this.storageArray.length;
    return hashValue;
}

private Dictionary<K, V> findBucket(K key) {
    if (key == null) {
        throw new IllegalArgumentException();
    }
    int hashValue = calculateHash(key);
    Dictionary<K, V> currentBucket = this.storageArray[hashValue];
    if (currentBucket == null) {
        currentBucket = newBucket.get();
        this.storageArray[hashValue] = currentBucket;
    }
    return currentBucket;
}
}