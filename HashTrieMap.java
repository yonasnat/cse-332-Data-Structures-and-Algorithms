package datastructures.dictionaries;


import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode present = (HashTrieNode)this.root;
        V element = null;
        for (A part : key) {
            if (present.pointers.get(part) == null) {
                present.pointers.put(part, new HashTrieNode());
            } else {
                present = present.pointers.get(part);
            }
        }
        element = present.value;
        present.value = value;
        if (element == null) {
            this.size++;
        }
        return element;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        HashTrieNode present = (HashTrieNode)this.root;
        for (A part: key) {
            present = present.pointers.get(part);
            if (present == null) {
                return null;
            }
        }
        return present.value;
    }


    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (this.root == null) {
            return false;
        }

        HashTrieNode present = (HashTrieNode)this.root;
        for (A part: key) {
            present = present.pointers.get(part);
            if (present == null) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode parent = null;
        A deletEnd = null;
        HashTrieNode current = (HashTrieNode) root;

        for (A part : key) {
            if (current == null) {
                return;
            }
            if (current.value != null || current.pointers.size() > 1) {
                parent = current;
                deletEnd = part;
            }
            if (!current.pointers.isEmpty()) {
                current = current.pointers.get(part);
            } else {
                return;
            }
        }
        if (current != null && current.value != null) {
            if (!current.pointers.isEmpty()) {
                current.value = null;
            } else if (deletEnd != null) {
                parent.pointers.remove(deletEnd);
            } else {
                root.value = null;
            }
            size--;
        }
    }


    @Override
    public void clear() {
        this.root = new HashTrieNode();
        this.size = 0;
    }
}
