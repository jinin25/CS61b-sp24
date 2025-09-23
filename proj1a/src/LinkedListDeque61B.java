import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B <T> implements Deque61B <T>{


    private node sentinel;
    private int size;

    private class node{
        node prev;
        T item;
        node next;

        node(node p, T i, node n){
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

    }

    @Override
    public void addFirst(T x) {
        node first = new node (sentinel, x, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size = size + 1;

    }

    @Override
    public void addLast(T x) {
        node last = new node(sentinel.prev, x, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size = size + 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        node curr = sentinel.next;
        while (curr != sentinel){
            returnList.add(curr.item);
            curr = curr.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel){
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

        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
