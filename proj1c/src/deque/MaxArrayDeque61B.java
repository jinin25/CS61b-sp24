package deque;
import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private Comparator<T> comparator;
    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            T current = get(i);
            if (c.compare(maxItem, current) < 0) {
                maxItem = current;
            }
        }
        return maxItem;
    }
}
