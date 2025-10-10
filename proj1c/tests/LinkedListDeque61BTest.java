import deque.LinkedListDeque61B;
import deque.Deque61B;
import deque.ArrayDeque61B;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

public class LinkedListDeque61BTest {

    @Test
    public void testToStringBasic() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("front");
        lld.addLast("middle");
        lld.addLast("back");

        String expected = "[front, middle, back]";
        assertThat(lld.toString()).isEqualTo(expected);
    }

    @Test
    public void testEqualsSameElements() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("a");
        lld1.addLast("b");
        lld2.addLast("a");
        lld2.addLast("b");

        // 应该相等，因为元素和顺序相同
        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void testEqualsDifferentSize() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();

        lld1.addLast(1);
        lld1.addLast(2);
        lld2.addLast(1);

        // 元素数量不同，应当不相等
        assertThat(lld1).isNotEqualTo(lld2);
    }

    @Test
    public void testEqualsDifferentType() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast(1);
        lld2.addLast("1");

        // 类型不同，应当不相等
        assertThat(lld1.equals(lld2)).isFalse();
    }

    @Test
    public void testIteratorWorks() {
        LinkedListDeque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(10);
        lld.addLast(20);
        lld.addLast(30);

        int sum = 0;
        for (int x : lld) {
            sum += x;
        }

        assertEquals(60, sum);
    }

    @Test
    public void testEqualsWithArrayDeque() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        Deque61B<String> ad = new ArrayDeque61B<>();

        lld.addLast("x");
        lld.addLast("y");
        ad.addLast("x");
        ad.addLast("y");

        // 只要接口相同、元素一致，也应判等
        assertThat(lld.equals(ad)).isTrue();
    }
}
