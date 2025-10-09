import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    //     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    public void add_first_from_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void add_last_from_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(2);
        assertThat(deque.get(0)).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void add_first_nonempty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addFirst(0);
        assertThat(deque.get(0)).isEqualTo(0);
        assertThat(deque.get(1)).isEqualTo(1);
        assertThat(deque.size()).isEqualTo(2);
    }

    @Test
    public void add_last_nonempty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.get(1)).isEqualTo(2);
    }

    @Test
    public void add_first_trigger_resize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) deque.addFirst(i);
        deque.addFirst(9);
        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.get(0)).isEqualTo(9);
        assertThat(deque.get(8)).isEqualTo(0);
    }

    @Test
    public void add_last_trigger_resize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) deque.addLast(i);
        deque.addLast(88);
        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.get(8)).isEqualTo(88);
    }

    /**
     * ---------------------- Add after Remove Tests ----------------------
     **/

    @Test
    public void add_first_after_remove_to_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeFirst();
        deque.removeFirst();
        deque.addFirst(3);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(3);
    }

    @Test
    public void add_last_after_remove_to_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeLast();
        deque.removeLast();
        deque.addLast(3);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(3);
    }

    /**
     * ---------------------- Remove Tests ----------------------
     **/

    @Test
    public void remove_first() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        Integer removed = deque.removeFirst();
        assertThat(removed).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(2);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void remove_last() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        Integer removed = deque.removeLast();
        assertThat(removed).isEqualTo(2);
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void remove_first_to_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeFirst();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void remove_last_to_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void remove_first_to_one() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(2);
    }

    @Test
    public void remove_last_to_one() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(1);
    }

    @Test
    public void remove_first_trigger_resize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 32; i++) deque.addLast(i);
        for (int i = 0; i < 25; i++) deque.removeFirst();
        assertThat(deque.get(0)).isEqualTo(25);
        assertThat(deque.size()).isEqualTo(7);
    }

    @Test
    public void remove_last_trigger_resize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 32; i++) deque.addLast(i);
        for (int i = 0; i < 25; i++) deque.removeLast();
        assertThat(deque.get(0)).isEqualTo(0);
        assertThat(deque.size()).isEqualTo(7);
    }

    /**
     * ---------------------- Get Tests ----------------------
     **/

    @Test
    public void get_valid() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        assertThat(deque.get(1)).isEqualTo("b");
    }

    @Test
    public void get_oob_large() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.get(10)).isNull();
    }

    @Test
    public void get_oob_neg() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.get(-1)).isNull();
    }

    /**
     * ---------------------- Size Tests ----------------------
     **/

    @Test
    public void size() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertThat(deque.size()).isEqualTo(2);
    }

    @Test
    public void size_after_remove_to_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void size_after_remove_from_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.removeFirst(); // removing from empty
        assertThat(deque.size()).isEqualTo(0);
    }

    /**
     * ---------------------- isEmpty Tests ----------------------
     **/

    @Test
    public void is_empty_true() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void is_empty_false() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.isEmpty()).isFalse();
    }

    /**
     * ---------------------- toList Tests ----------------------
     **/

    @Test
    public void to_list_empty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void to_list_nonempty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
    }

    /**
     * ---------------------- Advanced Resize Tests ----------------------
     **/

    @Test
    public void resize_up_and_resize_down() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) deque.addLast(i);
        assertThat(deque.size()).isEqualTo(20);
        for (int i = 0; i < 17; i++) deque.removeFirst();
        assertThat(deque.get(0)).isEqualTo(17);
        assertThat(deque.size()).isLessThan(10);
    }
}