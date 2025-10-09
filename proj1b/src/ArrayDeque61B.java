import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B <T> implements Deque61B<T> {

    private int size ;
    private int nextFirst;
    private int nextLast;
    private T deque[];

    public ArrayDeque61B() {
        deque = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    public void resizingUp(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int first = Math.floorMod(nextFirst + 1 , deque.length);
        for (int i = 0; i < size; i++) {
            newArray[i] = deque[(first + i) % deque.length];
        }
        nextFirst = capacity - 1;
        nextLast = size;
        deque = newArray;
    }

    @Override
    public void addFirst(T x) {
        if (size == deque.length) {
            resizingUp(deque.length * 2);
        }
        deque[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, deque.length);
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (size == deque.length) {
            resizingUp(deque.length * 2);
        }
        deque[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, deque.length);
        size += 1;

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int start = Math.floorMod(nextFirst + 1, deque.length);
        for (int i = 0; i < size; i++) {
            int idx = Math.floorMod(start + i, deque.length);
            returnList.add(deque[idx]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public void resizingDown(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        int first = Math.floorMod(nextFirst + 1 , deque.length);
        for (int i = 0; i < size; i++) {
            newArray[i] = deque[(first + i) % deque.length];
        }
        nextFirst = capacity - 1;
        nextLast = size;
        deque = newArray;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = Math.floorMod(nextFirst + 1, deque.length);
        T val = deque[nextFirst];
        deque[nextFirst] = null;
        size -= 1;
        if (deque.length >= 16 && size < deque.length / 4) {
            resizingDown(deque.length / 2);
        }
        return val;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = Math.floorMod(nextLast - 1, deque.length);
        T val = deque[nextLast];
        deque[nextLast] = null;
        size -= 1;
        if (deque.length >= 16 && size < deque.length / 4) {
            resizingDown(deque.length / 2);
        }
        return val;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int RecursiveIndex = Math.floorMod(nextFirst + 1 + index, deque.length);
        return deque[RecursiveIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
