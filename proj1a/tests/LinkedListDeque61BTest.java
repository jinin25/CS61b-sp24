import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
        */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.


    @Test
    public void isEmptyTestStringTrue() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void isEmptyTestString() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addFirst("");
        assertThat(lld.isEmpty()).isFalse();
    }

    @Test
    public void isEmptyTestInteger() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(0);
        assertThat(lld.isEmpty()).isFalse();
    }

    @Test
    public void sizeTestInteger() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(0);
        lld.addFirst(0);
        lld.addLast(0);
        assertThat(lld.size()).isEqualTo(3);
    }

    @Test
    public void sizeTestString1() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addFirst("");
        lld.addFirst("");
        lld.addLast("");
        assertThat(lld.size()).isEqualTo(3);
    }

    @Test
    public void sizeTestString2() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addFirst("652356");
        lld.addLast("zzzzzzzzz");
        lld.addFirst("++++++++");
        lld.addLast("       ");
        assertThat(lld.size()).isEqualTo(4);
    }

    @Test
    public void sizeTestEmpty() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void getTestInvalid() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(99);
        assertThat(lld.get(59841)).isEqualTo(null);
    }

    @Test
    public void getTestNegative() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(99);
        lld.addLast(-88);
        lld.addFirst(0);
        assertThat(lld.get(-9)).isEqualTo(null);
    }

    @Test
    public void getTestNull() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        assertThat(lld.get(0)).isEqualTo(null);
    }

    @Test
    public void getTest() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addFirst("happy");
        lld.addLast("Wednesday");
        assertThat(lld.get(1)).isEqualTo("Wednesday");
    }

    @Test
    public void getRecursiveTestInvalid() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(99);
        assertThat(lld.getRecursive(59841)).isEqualTo(null);
    }

    @Test
    public void getRecursiveTestNegative() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(99);
        lld.addLast(-88);
        lld.addFirst(0);
        assertThat(lld.getRecursive(-9)).isEqualTo(null);
    }

    @Test
    public void getRecursiveTestNull() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        assertThat(lld.getRecursive(0)).isEqualTo(null);
    }

    @Test
    public void getRecursiveTest() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addFirst("happy");
        lld.addLast("Wednesday");
        assertThat(lld.getRecursive(1)).isEqualTo("Wednesday");
    }

    @Test
    public void RemoveFirstAndRemoveLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        int remove1 = lld1.removeFirst();
        int remove2 = lld1.removeLast();
        int remove3 = lld1.removeFirst();

        assertThat(remove1).isEqualTo(-2);
        assertThat(remove2).isEqualTo(2);
        assertThat(remove3).isEqualTo(-1);
        assertThat(lld1.toList()).containsExactly(0, 1).inOrder();
    }

    @Test
    public void RemoveFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]

        String remove = lld1.removeFirst();

        assertThat(remove).isEqualTo("front");
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
    }

    @Test
    public void RemoveLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]

        String remove = lld1.removeLast();
        assertThat(remove).isEqualTo("back");
        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
    }

    @Test
    public void addFirstAfterRemoveToEmptyTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(1);
        lld.removeFirst(); // 现在空
        lld.addFirst(2);
        assertThat(lld.toList()).containsExactly(2).inOrder();
    }

    @Test
    public void addLastAfterRemoveToEmptyTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        lld.removeFirst(); // 现在空
        lld.addLast(88);
        assertThat(lld.toList()).containsExactly(88).inOrder();
    }

    @Test
    public void removeFirstToEmptyTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        int removed = lld.removeFirst();
        assertThat(removed).isEqualTo(1);
        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void removeLastToEmptyTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        int removed = lld.removeLast();
        assertThat(removed).isEqualTo(1);
        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void removeFirstToOneTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        lld.addLast(2);
        int removed = lld.removeFirst();
        assertThat(removed).isEqualTo(1);
        assertThat(lld.toList()).containsExactly(2).inOrder();
    }

    @Test
    public void removeLastToOneTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        lld.addLast(2);
        int removed = lld.removeLast();
        assertThat(removed).isEqualTo(2);
        assertThat(lld.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void sizeAfterRemoveToEmptyTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addFirst(1);
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void sizeAfterRemoveFromEmptyTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void toListEmptyTest() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        assertThat(lld.toList()).isEmpty();
    }
}