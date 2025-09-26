package algorithms;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class deterministicTest {

    @Test
    void testCompareToSortRandomTrials() {
        Random rand = new Random(42);
        for (int trial = 0; trial < 20; trial++) { // меньше тестов для компактного вывода
            int n = rand.nextInt(50) + 1; // массивы маленькие, n in [1,50]
            int[] arr = rand.ints(n, -100, 100).toArray(); // маленькие числа
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            for (int i = 0; i < n; i++) {
                int[] arrCopy = arr.clone();
                deterministic.Metrics m = new deterministic.Metrics();
                int sel = deterministic.select(arrCopy, i, m);
                assertEquals(sorted[i], sel, "mismatch at index " + i + " in trial " + trial);
            }

            // компактный вывод
            System.out.println("trial " + trial + ": length=" + n + ", min=" + Arrays.stream(arr).min().orElse(0) + ", max=" + Arrays.stream(arr).max().orElse(0));
        }
    }

    @Test
    void testAllEqual() {
        int[] arr = new int[10];
        Arrays.fill(arr, 42);
        deterministic.Metrics m = new deterministic.Metrics();
        assertEquals(42, deterministic.select(arr.clone(), 0, m));
        assertEquals(42, deterministic.select(arr.clone(), 9, m));
        System.out.println("all equal test passed, value=42");
    }

    @Test
    void testAdversarial() {
        int[] arr = {100, 99, 98, 97, 96, 95, 94, 93, 92, 91};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        for (int k = 0; k < arr.length; k++) {
            deterministic.Metrics m = new deterministic.Metrics();
            int val = deterministic.select(arr.clone(), k, m);
            assertEquals(sorted[k], val, "k=" + k);
        }

        System.out.println("adversarial test passed, length=" + arr.length);
    }
}