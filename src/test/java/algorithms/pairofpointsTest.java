package algorithms;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
class pairofpointsTest {
    @Test
    void testsmall() {
        Random rand = new Random(42);
        for (int t = 0; t < 10; ++t) {
            int n = rand.nextInt(1999) + 2; // n in [2,2000]
            pairofpoints.Point[] pts = new pairofpoints.Point[n];
            for (int i = 0; i < n; ++i)
                pts[i] = new pairofpoints.Point(rand.nextDouble() * 10000 - 5000, rand.nextDouble() * 10000 - 5000);
            pairofpoints.Metrics m1 = new pairofpoints.Metrics();
            pairofpoints.Metrics m2 = new pairofpoints.Metrics();
            pairofpoints.Result fast = pairofpoints.closestPair(pts, m1);
            pairofpoints.Result brute = pairofpoints.bruteForceAll(pts, m2);
            assertTrue(Math.abs(fast.dist - brute.dist) < 1e-9, "mismatch n=" + n + " | fast=" + fast.dist + " brute=" + brute.dist);
        }
    }
    @Test
    void simpletest() {
        pairofpoints.Point[] pts = {
                new pairofpoints.Point(0, 0),
                new pairofpoints.Point(1, 0),
                new pairofpoints.Point(0, 1),
                new pairofpoints.Point(2, 2)
        };
        pairofpoints.Metrics m = new pairofpoints.Metrics();
        pairofpoints.Result result = pairofpoints.closestPair(pts, m);
        assertEquals(1.0, result.dist, 1e-9);
    }
    @Test
    void testlarge() {
        Random rand = new Random(123);
        int n = 100_000;
        pairofpoints.Point[] pts = new pairofpoints.Point[n];
        for (int i = 0; i < n; ++i)
            pts[i] = new pairofpoints.Point(rand.nextDouble() * 1e8, rand.nextDouble() * 1e8);
        pairofpoints.Metrics m = new pairofpoints.Metrics();
        pairofpoints.Result result = pairofpoints.closestPair(pts, m);
        assertTrue(result.dist >= 0 && result.dist < Double.POSITIVE_INFINITY, "rwsult should be valid");
    }
}