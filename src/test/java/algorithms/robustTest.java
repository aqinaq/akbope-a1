package algorithms;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i - 1] > arr[i]) return false;
        return true;
    }
    @Test
    void testRandomArraysAreSorted() {
        Random rand = new Random(42);
        for (int t = 0; t < 10; t++) {
            int n = rand.nextInt(50) + 100;
            int[] arr = rand.ints(n, -100, 100).toArray();
            int[] copy = arr.clone();

            robust.Metrics m = new robust.Metrics();
            robust.sort(arr, m);
            Arrays.sort(copy);

            System.out.println("test " + t + ": length=" + n + ", sorted=" + isSorted(arr));

            assertArrayEquals(copy, arr);
        }
    }

    @Test
    void testAdversarialArrays() {
        int[][] cases = {
                {}, {1}, {2,1}, {1,2,3,4,5}, {5,4,3,2,1},
                {1,1,1,1,1}, {2,3,2,3,2,3,2,3},
                {Integer.MAX_VALUE,Integer.MIN_VALUE,0,-1,1}
        };

        for (int[] arr: cases) {
            int[] copy = arr.clone();
            robust.Metrics m = new robust.Metrics();
            robust.sort(copy, m);
            System.out.println("case length=" + arr.length +", sorted=" + isSorted(copy));
            assertTrue(isSorted(copy));
        }
    }

    @Test
    void testRecursionDepthBounded() {
        Random rand = new Random(123);
        for (int trial = 0; trial < 10; trial++) {
            int n = 100_000;
            int[] arr = rand.ints(n, -10_000_000, 10_000_000).toArray();
            robust.Metrics m = new robust.Metrics();
            robust.sort(arr, m);
            int bound = 2 * (int) Math.floor(Math.log(n)/Math.log(2)) + 16;
            System.out.println("trial " + trial + ": max depth=" + m.maxDepth);
            assertTrue(m.maxDepth <= bound);
        }
    }
}