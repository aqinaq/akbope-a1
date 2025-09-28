package algorithms;
import java.util.Random;
public class robust {
    private static final Random rand = new Random();

    public static class Metrics {
        public long comparisons = 0;
        public long swaps = 0;
        public long maxDepth = 0;
    }

    public static void sort(int[] a, Metrics metrics) {
        quickSort(a, 0, a.length - 1, 1, metrics);
    }
    private static void quickSort(int[] a, int left, int right, int depth, Metrics metrics) {
        while (left < right) {
            metrics.maxDepth = Math.max(metrics.maxDepth, depth);
            int pivotIdx = left + rand.nextInt(right - left + 1);
            swap(a, left, pivotIdx, metrics);

            int pivot = a[left];
            int i = left + 1;
            for (int j = left + 1; j <= right; j++) {
                metrics.comparisons++;
                if (a[j] < pivot) {
                    swap(a, i, j, metrics);
                    i++;
                }
            }
            swap(a, left, i - 1, metrics);
            int pivotFinal = i - 1;

            int leftLen = pivotFinal - left;
            int rightLen = right - pivotFinal;
            if (leftLen < rightLen) {
                if (leftLen > 0) quickSort(a, left, pivotFinal - 1, depth + 1, metrics);
                left = pivotFinal + 1;
            } else {
                if (rightLen > 0) quickSort(a, pivotFinal + 1, right, depth + 1, metrics);
                right = pivotFinal - 1;
            }
        }
    }

    private static void swap(int[] a, int i, int j, Metrics metrics) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        metrics.swaps++;
    }

}