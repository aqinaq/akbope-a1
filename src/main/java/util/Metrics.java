package util;

public class Metrics {
        public int currentDepth = 0;
        public int maxDepth = 0;
        public long comparisons = 0;
        public long allocations = 0;
        public long startTime;
        public long endTime;

        public void start() {
            startTime = System.nanoTime();
        }

        public void stop() {
            endTime = System.nanoTime();
        }

        public void enter() {
            currentDepth++;
            if (currentDepth > maxDepth) {
                maxDepth = currentDepth;
            }
        }

        public void exit() {
            currentDepth--;
        }

        public void countComparison() {
            comparisons++;
        }

        public void countAlloc() {
            allocations++;
        }

        public long getElapsedMs() {
            return (endTime - startTime) / 1_000_000;
        }
    }

