package algorithms;
import java.util.Random;
public class util {
    private static final Random rand = new Random();
    public static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    public static <T> void swap(T[] a, int i, int j) {
        if (i == j) return;
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(a, i, j);
        }
    }
    public static <T> void shuffle(T[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(a, i, j);
        }
    }
    public static int partition(int[] a, int left, int right, int pivotIdx, Metrics metrics) {
        int pivot = a[pivotIdx];
        swap(a, pivotIdx, right);
        int store = left;
        for (int i = left; i < right; i++) {
            if (metrics != null) metrics.comparisons++;
            if (a[i] < pivot) {
                swap(a, store, i);
                if (metrics != null) metrics.swaps++;
                store++;
            }
        }
        swap(a, store, right);
        if (metrics != null) metrics.swaps++;
        return store;
    }
    public static <T extends Comparable<T>> int partition(T[] a, int left, int right, int pivotIdx, Metrics metrics) {
        T pivot = a[pivotIdx];
        swap(a, pivotIdx, right);
        int store = left;
        for (int i = left; i < right; i++) {
            if (metrics != null) metrics.comparisons++;
            if (a[i].compareTo(pivot) < 0) {
                swap(a, store, i);
                if (metrics != null) metrics.swaps++;
                store++;
            }
        }
        swap(a, store, right);
        if (metrics != null) metrics.swaps++;
        return store;
    }
    public static void guardNotEmpty(int[] a) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("Array must not be empty");
    }
    public static <T> void guardNotEmpty(T[] a) {
        if (a == null || a.length == 0)
            throw new IllegalArgumentException("Array must not be empty");
    }
    public static void guardIndex(int n, int k) {
        if (k < 0 || k >= n) throw new IllegalArgumentException("k out of bounds");
    }
    public static class Metrics extends robust.Metrics {
        public long comparisons = 0;
        public long swaps = 0;
        public long maxDepth = 0;
    }
}