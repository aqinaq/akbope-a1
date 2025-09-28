package algorithms;

public class msort {
    private final Metrics metrics;
    private int[] buffer;

    public msort(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort ( int[] arr){
        if (arr.length == 0) return;
        buffer = new int[arr.length];
        metrics.reset();
        sort(arr, 0, arr.length - 1);
    }

    private void sort ( int[] arr, int left, int right){
        metrics.enterrecursion();
        if (right - left + 1 <= 16) {
            insertionSort(arr, left, right);
            metrics.exitrecursion();
            return;
        }
        int mid = (left + right) / 2;
        sort(arr, left, mid);
        sort(arr, mid + 1, right);
        merge(arr, left, mid, right);
        metrics.exitrecursion();
    }
    private void merge ( int[] arr, int left, int mid, int right){
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            metrics.Comparisons();
            if (arr[i] <= arr[j]) buffer[k++] = arr[i++];
            else buffer[k++] = arr[j++];
        }
        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];
        for (int t = left; t <= right; t++) arr[t] = buffer[t];
    }
    private void insertionSort ( int[] arr, int left, int right){
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                metrics.Comparisons();
                arr[j + 1] = arr[j];
                j--;
            }
            if (j >= left) metrics.Comparisons();
            arr[j + 1] = key;
        }
    }
}
