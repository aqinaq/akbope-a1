package algorithms;

import java.util.Random;
import java.util.Arrays;
public class cli {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java algorithms.CLI <algorithm> <n>");
            System.out.println("Algorithms: mergesort quicksort select closestpair");
            return;
        }
        String algo = args[0];
        int n = Integer.parseInt(args[1]);
        Random rand = new Random(42);

        if ("mergesort".equalsIgnoreCase(algo)) {
            int[] arr = rand.ints(n, -10_000, 10_000).toArray();
            Metrics metrics = new Metrics();
            msort sorter = new msort(metrics);
            sorter.sort(arr);
            metrics.writeToCSV("metrics.csv", "mergesort");
            System.out.println("mergesort done: comparisons=" + metrics.comparison + ", maxrecursion=" + metrics.maxrecursion);
        } else if ("quicksort".equalsIgnoreCase(algo)) {
            int[] arr = rand.ints(n, -10_000, 10_000).toArray();
            robust.Metrics metrics = new robust.Metrics();
            robust.sort(arr, metrics);
            // Write CSV manually since robust.Metrics is not compatible with your Metrics class
            try (java.io.FileWriter writer = new java.io.FileWriter("metrics.csv", true)) {
                if (new java.io.File("metrics.csv").length() == 0)
                    writer.write("algorithm,comparisons,maxDepth\n");
                writer.write("quicksort," + metrics.comparisons + "," + metrics.maxDepth + "\n");
            } catch (Exception e) { e.printStackTrace(); }
            System.out.println("quicksort done: comparisons=" + metrics.comparisons + ", maxDepth=" + metrics.maxDepth);
        } else if ("select".equalsIgnoreCase(algo)) {
            int[] arr = rand.ints(n, -10_000, 10_000).toArray();
            int k = n / 2;
            deterministic.Metrics metrics = new deterministic.Metrics();
            int result = deterministic.select(arr, k, metrics);
            try (java.io.FileWriter writer = new java.io.FileWriter("metrics.csv", true)) {
                if (new java.io.File("metrics.csv").length() == 0)
                    writer.write("algorithm,comparisons,maxDepth\n");
                writer.write("select," + metrics.comparisons + "," + metrics.maxDepth + "\n");
            } catch (Exception e) { e.printStackTrace(); }
            System.out.println("select done: comparisons=" + metrics.comparisons + ", maxDepth=" + metrics.maxDepth + ", result=" + result);
        } else if ("closestpair".equalsIgnoreCase(algo)) {
            pairofpoints.Point[] pts = new pairofpoints.Point[n];
            for (int i = 0; i < n; ++i)
                pts[i] = new pairofpoints.Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
            pairofpoints.Metrics metrics = new pairofpoints.Metrics();
            pairofpoints.Result res = pairofpoints.closestPair(pts, metrics);
            try (java.io.FileWriter writer = new java.io.FileWriter("metrics.csv", true)) {
                if (new java.io.File("metrics.csv").length() == 0)
                    writer.write("algorithm,comparisons,maxDepth\n");
                writer.write("closestpair," + metrics.comparisons + "," + metrics.maxDepth + "\n");
            } catch (Exception e) { e.printStackTrace(); }
            System.out.println("closestpair done: dist=" + res.dist + ", comparisons=" + metrics.comparisons + ", maxDepth=" + metrics.maxDepth);
        } else {
            System.out.println("Unknown algorithm: " + algo);
        }
    }
}