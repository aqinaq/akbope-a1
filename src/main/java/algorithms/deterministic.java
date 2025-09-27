package algorithms;
public class deterministic {
    public static class Metrics {
        public long comparisons = 0;
        public long swaps = 0;
        public long maxDepth = 0;
    }
    public static int select(int[] a, int k, Metrics metrics) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of bounds");
        return momSelect(a, 0, a.length - 1, k, 1, metrics);
    }
    private static int momSelect(int[] a, int left, int right, int k, int depth, Metrics metrics) {
        while (true) {
            metrics.maxDepth = Math.max(metrics.maxDepth, depth);
            int n = right - left + 1;
            if (n <= 5) {
                insertionSort(a, left, right, metrics);
                return a[left + k];
            }
            int numGroups = (n + 4) / 5;
            for (int i = 0; i < numGroups; i++) {
                int l = left + i * 5;
                int r = Math.min(l + 4, right);
                insertionSort(a, l, r, metrics);
                int medianIdx = l + (r - l) / 2;
                swap(a, left + i, medianIdx, metrics);
            }
            int medianOfMedians = momSelect(a, left, left + numGroups - 1, numGroups / 2, depth + 1, metrics);

            int pivotIdx = partition(a, left, right, medianOfMedians, metrics);

            int rank = pivotIdx - left;
            if (k == rank) {
                return a[pivotIdx];
            }
            int leftSize = rank;
            int rightSize = right - pivotIdx;
            if (k < rank) {
                if (leftSize < rightSize) { // prefer smaller side
                    right = pivotIdx - 1;
                    depth++;
                } else {
                    return momSelect(a, left, pivotIdx - 1, k, depth + 1, metrics);
                }
            } else {
                if (rightSize < leftSize) {
                    k = k - rank - 1;
                    left = pivotIdx + 1;
                    depth++;
                } else {
                    return momSelect(a, pivotIdx + 1, right, k - rank - 1, depth + 1, metrics);
                }
            }
        }
    }
    private static int partition(int[] a, int left, int right, int pivotValue, Metrics metrics) {
        int pivotIdx = left;
        while (pivotIdx <= right && a[pivotIdx] != pivotValue) pivotIdx++;
        swap(a, pivotIdx, right, metrics);
        int store = left;
        for (int i = left; i < right; i++) {
            metrics.comparisons++;
            if (a[i] < pivotValue) {
                swap(a, store, i, metrics);
                store++;
            }
        }
        swap(a, store, right, metrics);
        return store;
    }
    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left && a[j] > key) {
                metrics.comparisons++;
                a[j + 1] = a[j];
                j--;
            }
            metrics.comparisons++;
            a[j + 1] = key;
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