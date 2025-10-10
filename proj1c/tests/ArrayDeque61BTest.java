import deque.LinkedListDeque61B;
import deque.Deque61B;
import deque.ArrayDeque61B;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

public class ArrayDeque61BTest {

    @Test
    public void testToStringBasic() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        String expected = "[front, middle, back]";
        assertThat(ad.toString()).isEqualTo(expected);
    }

    @Test
    public void testEqualsSameElements() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad1.addLast("a");
        ad1.addLast("b");
        ad2.addLast("a");
        ad2.addLast("b");

        assertThat(ad1).isEqualTo(ad2);
    }

    @Test
    public void testEqualsDifferentElements() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        Deque61B<Integer> ad2 = new ArrayDeque61B<>();

        ad1.addLast(1);
        ad1.addLast(2);
        ad2.addLast(1);
        ad2.addLast(3);

        assertThat(ad1).isNotEqualTo(ad2);
    }

    @Test
    public void testIteratorWorks() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(5);
        ad.addLast(10);
        ad.addLast(15);

        int total = 0;
        for (int x : ad) {
            total += x;
        }

        assertEquals(30, total);
    }

    @Test
    public void testEqualsWithLinkedListDeque() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> lld = new LinkedListDeque61B<>();

        ad.addLast("x");
        ad.addLast("y");
        lld.addLast("x");
        lld.addLast("y");

        // 依然相等
        assertThat(ad.equals(lld)).isTrue();
    }
}