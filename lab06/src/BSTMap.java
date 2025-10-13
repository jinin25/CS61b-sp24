import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size;
    private class Node {
        K key;
        V value;
        Node left, right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private Node root;

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node root, K key, V value) {
        if (root == null) {
            size ++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = put(root.left, key, value);
        }
        else if (cmp > 0) {
            root.right = put(root.right, key, value);
        }
        else {
            root.value = value;
        }
        return root;
    }

    @Override
    public V get(K key) {
        Node node = get(root, key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x;
        }
    }
    @Override
    public boolean containsKey(K key) {
        Node node = get(root, key);
        return node != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
       Set<K> set = new TreeSet<>();
       inOrder(root, set);
       return set;
    }

    public void printInOrder() {
        Set<K> set = keySet();
        for(K key : set) {
            System.out.println(key);
        }
    }

    private void inOrder(Node x, Set<K> set) {
        if (x == null) {
            return;
        }
        inOrder(x.left, set);
        set.add(x.key);
        inOrder(x.right, set);
    }

    @Override
    public V remove(K key) {
        V returnValue = get(key);
        if(returnValue == null) {
            return null;
        }
        root = remove(root, key);
        return returnValue;
    }

    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        }
        else{
            if (x.left == null) {
                size--;
                return x.right;
            }
            if (x.right == null) {
                size--;
                return x.left;
            }

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
            size--;
        }
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {

        Set<K> set;
        int count;
        Iterator<K> it;

        public BSTMapIterator() {
            set = keySet();
            count = 0;
            it = set.iterator();
        }
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public K next() {
            count++;
            return it.next();
        }
    }
}

