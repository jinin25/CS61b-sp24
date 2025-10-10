import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void testMaxWithNaturalOrder() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        mad.addFirst(5);
        mad.addLast(15);
        mad.addFirst(12);
        assertThat(mad.max()).isEqualTo(15);
    }

    @Test
    public void testMaxWithReverseOrder() {
        Comparator<Integer> c = (a, b) -> Integer.compare(b, a);
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(c);
        mad.addFirst(8);
        mad.addLast(-2);
        mad.addFirst(0);
        assertThat(mad.max()).isEqualTo(-2);
    }

    @Test
    public void testMaxWithCustomOrder() {
        Comparator<String> c = (a, b) -> a.length() - b.length();
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<String>(c);
        mad.addFirst("happy");
        mad.addLast("a");
        mad.addFirst("good");
        mad.addFirst("weekend");
        assertThat(mad.max()).isEqualTo("weekend");
    }

    @Test
    public void testDifferentCpt() {
        Comparator<Integer> c1 = (a, b) -> Integer.compare(a, b);
        Comparator<Integer> c2 = (a, b) -> Integer.compare(b, a);
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<Integer>(c2);
        mad.addFirst(8);
        mad.addLast(-2);
        mad.addFirst(0);
        assertThat(mad.max(c1)).isEqualTo(8);
    }

}
