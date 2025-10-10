package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private Node sentinel;
    private int size;

    private class Node {
        Node prev;
        T item;
        Node next;

        Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node first = new Node(sentinel, x, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size = size + 1;

    }

    @Override
    public void addLast(T x) {
        Node last = new Node(sentinel.prev, x, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size = size + 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node curr = sentinel.next;
        while (curr != sentinel) {
            returnList.add(curr.item);
            curr = curr.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size = size - 1;
        return result;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size = size - 1;
        return result;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node curr = sentinel;
        for (int i = 0; i <= index; i++) {
            curr = curr.next;
        }
        return curr.item;
    }

    private T getRecursiveHelper(Node x, int index) {
        if (x == sentinel) {
            return null;
        }
        if (index == 0) {
            return x.item;
        }
        return getRecursiveHelper(x.next, index - 1);
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || size == 0) {
            return null;
        }
        return (getRecursiveHelper(sentinel.next, index));
    }



    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {

        private Node currentNode;

        public LinkedListIterator() {
            currentNode = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return currentNode != sentinel;
        }

        @Override
        public T next() {
            T returnItem = currentNode.item;
            currentNode = currentNode.next;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque61B<?> otherDeque) {
            if (this.size != otherDeque.size()) {
                return false;
            }
            Iterator<T> thisIt = this.iterator();
            Iterator<?> otherIt = otherDeque.iterator();

            while (thisIt.hasNext() && otherIt.hasNext()) {
                T a = thisIt.next();
                Object b = otherIt.next();

                if ((a == null && b != null) || (a != null && b == null)) {
                    return false;
                }
                if (a != null && !a.equals(b)) {
                    return false;
                }
            }
            return !(thisIt.hasNext() || otherIt.hasNext());
        }
        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }

}
