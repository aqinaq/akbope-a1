package algorithms;

import java.util.Arrays;
import java.util.Comparator;

public class pairofpoints {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }
    public static class Result {
        public final double dist;
        public final Point p1, p2;
        public Result(double dist, Point p1, Point p2) { this.dist = dist; this.p1 = p1; this.p2 = p2; }
    }

    public static class Metrics extends util.Metrics {
        public long comparisons = 0;
        public long maxDepth = 0;
    }

    public static Result closestPair(Point[] points, Metrics metrics) {
        Point[] px = points.clone();
        Point[] py = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));
        return closest(px, py, 0, px.length - 1, 1, metrics);
    }

    private static Result closest(Point[] px, Point[] py, int left, int right, int depth, Metrics metrics) {
        metrics.maxDepth = Math.max(metrics.maxDepth, depth);
        int n = right - left + 1;
        if (n <= 3) return bruteForce(px, left, right, metrics);

        int mid = (left + right) / 2;
        double midX = px[mid].x;

        Point[] pyl = new Point[mid - left + 1];
        Point[] pyr = new Point[right - mid];
        int li = 0, ri = 0;
        for (int i = 0; i < py.length; i++) {
            if (py[i].x <= midX && li < pyl.length) pyl[li++] = py[i];
            else pyr[ri++] = py[i];
        }

        Result leftRes = closest(px, pyl, left, mid, depth + 1, metrics);
        Result rightRes = closest(px, pyr, mid + 1, right, depth + 1, metrics);

        Result best = (leftRes.dist < rightRes.dist) ? leftRes : rightRes;
        double d = best.dist;

        Point[] strip = new Point[n];
        int stripSize = 0;
        for (int i = 0; i < py.length; i++) {
            if (Math.abs(py[i].x - midX) < d)
                strip[stripSize++] = py[i];
        }
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < d; j++) {
                metrics.comparisons++;
                double dist = dist(strip[i], strip[j]);
                if (dist < best.dist) {
                    best = new Result(dist, strip[i], strip[j]);
                }
            }
        }
        return best;
    }

    private static Result bruteForce(Point[] pts, int left, int right, Metrics metrics) {
        double minDist = Double.POSITIVE_INFINITY;
        Point p1 = null, p2 = null;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                metrics.comparisons++;
                double d = dist(pts[i], pts[j]);
                if (d < minDist) {
                    minDist = d;
                    p1 = pts[i];
                    p2 = pts[j];
                }
            }
        }
        return new Result(minDist, p1, p2);
    }
    private static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }


    public static Result bruteForceAll(Point[] points, Metrics metrics) {
        return bruteForce(points, 0, points.length - 1, metrics);
    }
}