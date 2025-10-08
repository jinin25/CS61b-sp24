import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class UnionFindTest {

    /**
     * Checks that the initial state of the disjoint sets are correct (this will pass with the skeleton
     * code, but ensure it still passes after all parts are implemented).
     */
    @Test
    public void initialStateTest() {
        UnionFind uf = new UnionFind(4);
        assertThat(uf.connected(0, 1)).isFalse();
        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(0, 3)).isFalse();
        assertThat(uf.connected(1, 2)).isFalse();
        assertThat(uf.connected(1, 3)).isFalse();
        assertThat(uf.connected(2, 3)).isFalse();
    }

    /**
     * Checks that invalid inputs are handled correctly.
     */
    @Test
    public void illegalFindTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.find(10);
            fail("Cannot find an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            uf.union(1, 10);
            fail("Cannot union with an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Checks that union is done correctly (including the tie-breaking scheme).
     */
    @Test
    public void basicUnionTest() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        assertThat(uf.find(0)).isEqualTo(1);
        uf.union(2, 3);
        assertThat(uf.find(2)).isEqualTo(3);
        uf.union(0, 2);
        assertThat(uf.find(1)).isEqualTo(3);

        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);
        uf.union(4, 8);
        uf.union(4, 6);

        assertThat(uf.find(5)).isEqualTo(9);
        assertThat(uf.find(7)).isEqualTo(9);
        assertThat(uf.find(8)).isEqualTo(9);

        uf.union(9, 2);
        assertThat(uf.find(3)).isEqualTo(9);
    }

    /**
     * Unions the same item with itself. Calls on find and checks that the outputs are correct.
     */
    @Test
    public void sameUnionTest() {
        UnionFind uf = new UnionFind(4);
        uf.union(1, 1);
        for (int i = 0; i < 4; i += 1) {
            assertThat(uf.find(i)).isEqualTo(i);
        }
    }

    /**
     * Write your own tests below here to verify for correctness. The given tests are not comprehensive.
     * Specifically, you may want to write a test for path compression and to check for the correctness
     * of all methods in your implementation.
     */

    /**
     * Tests deep tree before and after path compression.
     * This ensures find() properly compresses the path.
     */
    @Test
    public void pathCompressionTest() {
        UnionFind uf = new UnionFind(10);
        // create a chain: 0-1-2-3-4
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(3, 4);

        int before = uf.find(0);
        assertThat(before).isEqualTo(1); // should reach the root 1

        // After path compression, all nodes should now directly point to 1
        for (int i = 0; i <= 4; i++) {
            assertThat(uf.find(i)).isEqualTo(1);
        }
    }

    /**
     * Tests repeated unions between already connected elements.
     * Should not break anything or change internal structure.
     */
    @Test
    public void redundantUnionTest() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        int rootBefore = uf.find(0);
        uf.union(0, 2);
        uf.union(1, 2);
        int rootAfter = uf.find(0);
        assertThat(rootBefore).isEqualTo(rootAfter);
        assertThat(uf.connected(0, 2)).isTrue();
    }

    /**
     * Tests union by size/rank: smaller trees should attach to larger ones.
     */
    @Test
    public void unionBySizeTest() {
        UnionFind uf = new UnionFind(6);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);

        // make 0-1-2-3 a bigger tree
        uf.union(0, 2);

        // now 4-5 should merge into 0-1-2-3
        uf.union(4, 0);

        int root = uf.find(5);
        assertThat(uf.find(3)).isEqualTo(root);
        assertThat(uf.find(1)).isEqualTo(root);
        assertThat(uf.find(0)).isEqualTo(root);
    }

    /**
     * Tests multiple disjoint sets remain separated.
     */
    @Test
    public void disjointSetsRemainSeparateTest() {
        UnionFind uf = new UnionFind(8);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        // 6 and 7 untouched

        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(3, 5)).isFalse();
        assertThat(uf.connected(6, 7)).isFalse();

        uf.union(6, 7);
        assertThat(uf.connected(6, 7)).isTrue();
    }

    /**
     * Randomized test to ensure consistency under arbitrary merges.
     * Simulates a stress scenario.
     */
    @Test
    public void randomMergesConsistencyTest() {
        UnionFind uf = new UnionFind(10);
        int[][] merges = {
                {0, 1}, {2, 3}, {4, 5}, {6, 7},
                {1, 2}, {3, 4}, {5, 6}
        };
        for (int[] p : merges) {
            uf.union(p[0], p[1]);
        }

        // Now everything from 0 to 7 should be connected
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                assertThat(uf.connected(i, j)).isTrue();
            }
        }

        // 8 and 9 remain isolated
        assertThat(uf.connected(0, 8)).isFalse();
        assertThat(uf.connected(9, 8)).isFalse();
    }

    /**
     * Tests find() after many unions and ensures no index errors occur.
     */
    @Test
    public void largeScaleUnionFindTest() {
        UnionFind uf = new UnionFind(1000);
        for (int i = 1; i < 1000; i++) {
            uf.union(0, i);
        }
        for (int i = 1; i < 1000; i++) {
            assertThat(uf.connected(0, i)).isTrue();
        }
        assertThat(uf.find(999)).isEqualTo(uf.find(0));
    }

    /**
     * Tests invalid input for connected() method.
     */
    @Test
    public void illegalConnectedTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.connected(-1, 2);
            fail("Negative index should throw an exception!");
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            uf.connected(2, 10);
            fail("Index out of bounds should throw an exception!");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}


