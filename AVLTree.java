package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    public class AVLNode extends BSTNode {
        private int heightDiff;

        public AVLNode(K key, V value) {
            super(key, value);
            this.heightDiff = 0;
        }
        private AVLNode leftC() {
            return (AVLNode)this.children[0];
        }
        private AVLNode rightC() {
            return (AVLNode) this.children[1];
        }
    }
    public AVLTree() {
        super();
    }

    private int heightGet(AVLNode node) {
        if (node == null) {
            return -1;
        }
        return node.heightDiff;
    }
    private void heightSet(AVLNode node) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        int height = heightGet(node.rightC());
        int top = heightGet(node.leftC());
        if (top < height) {
            top = height;
        }
        node.heightDiff = top + 1;
    }

    private int getB(AVLNode node) {
        if (node == null) {
            return -1;
        }
        return heightGet(node.rightC()) - heightGet(node.leftC());
    }

    @Override
    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            throw new IllegalArgumentException();
        }
        V end = find(key);
        this.root = helpInsert((AVLNode) this.root, key, value);
        return end;
    }

    private AVLNode helpInsert(AVLNode node, K key, V value) {
        if (node == null) {
            this.size++;
            return new AVLNode(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.children[0] = helpInsert(node.leftC(), key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.children[1] = helpInsert(node.rightC(), key, value);
        } else {
            node.value = value;
            return node;
        }
        node = rotation(node);
        heightSet(node);
        return node;
    }

    private AVLNode rotation(AVLNode node) {
        int numb = getB(node);
        if (numb > 1) {
            int right = getB(node.rightC());
            if (right >= 0) {
                node = moveL(node);
            } else {
                node.children[1] = moveR(node.rightC());
                node = moveL(node);
            }
        }
        if (numb < -1) {
            int left = getB(node.leftC());
            if (left <= 0) {
                node = moveR(node);
            } else {
                node.children[0] = moveL(node.leftC());
                node = moveR(node);
            }
        }
        return node;
    }

    private AVLNode moveR(AVLNode node) {
        AVLNode child = node.leftC();
        node.children[0] = child.rightC();
        child.children[1] = node;
        heightSet(child);
        heightSet(node);
        return child;
    }

    private AVLNode moveL(AVLNode node) {
        AVLNode child = node.rightC();
        node.children[1] = child.leftC();
        child.children[0] = node;
        heightSet(child);
        heightSet(node);
        return child;
    }

  }
