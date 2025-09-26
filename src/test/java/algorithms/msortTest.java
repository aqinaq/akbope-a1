package algorithms;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class msortTest {
    @Test
    void testrandom() {
        int[] arr = new Random().ints(100, 0, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        msort ms = new msort(metrics);
        ms.sort(arr);

        assertArrayEquals(expected, arr);
        System.out.println("Comparisons: " + metrics.comparison);
        System.out.println("Max Recursion: " + metrics.maxrecursion);
    }
}