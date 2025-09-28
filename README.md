# Assignment 1 — Divide and Conquer Algorithms
# Overview

This project is about learning and testing classic divide-and-conquer algorithms.
I implemented MergeSort, QuickSort (robust), Deterministic Select (Median-of-Medians), and Closest Pair of Points.
I also made a CLI, utilities, metrics collection, and tests for each algorithm.

## The main goals were:
Write safe recursive algorithms.
analyze running times with Master Theorem and Akra–Bazzi.
Collect data: execution time, recursion depth, comparisons, allocations.
Compare results from theory with experiments.

## Learning Goals
Practice divide-and-conquer design and safe recursion.
Understand recurrences and solve them with Master Theorem / Akra–Bazzi.
Collect metrics from real runs and show plots.
Write a short but clear report.

## Algorithms
## 1. MergeSort (msort.java) (Θ(n log n))

Idea: split array into halves, sort each half, then merge.

Optimizations:
Use one buffer array (less memory use).
For small arrays, switch to insertion sort.
Recurrence:
T(n) = 2T(n/2) + Θ(n) → Master Theorem Case 2 → Θ(n log n).
Depth: about log₂(n).

## 2. QuickSort (robust.java) (average Θ(n log n))

Idea: choose pivot, partition array, recurse.
Optimizations:
Use random pivot (avoids worst case).
Always recurse on smaller side, iterate on bigger one (safe depth).
Recurrence:
Average: T(n) = T(k) + T(n-k-1) + Θ(n) → Θ(n log n).
Worst: Θ(n²), but very rare with random pivot.
Depth: about log₂(n).

## 3. Deterministic Select (deterministic.java)(Median-of-Medians, Θ(n))

Idea:
Split into groups of 5, take medians, choose pivot.
Partition in place.
Only recurse into one side (where the k-th element is).
Recurrence:
T(n) = T(n/5) + T(7n/10) + Θ(n) → Θ(n).
Depth: logarithmic, smaller than sorting.

## 4. Closest Pair of Points (pairofpoint.java) (Θ(n log n))

Idea:
Sort points by x, then split.
Check middle strip sorted by y.
Only check up to 7–8 neighbors.
Recurrence:
T(n) = 2T(n/2) + Θ(n) → Master Theorem Case 2 → Θ(n log n).
Depth: log₂(n).

## Metrics Collection
Time: measured in ms/ns depending on algorithm.
Recursion depth: tracked with a counter.
Comparisons: counted on each comparison.
Allocations: counted for arrays and buffers.
Example CSV output (Closest Pair):

size,time_ms,comp,alloc,maxdepth
10,0.005,38,20,3
100,0.030,887,200,6
1000,0.950,13520,2000,9
10000,6.500,180969,20000,13

Plots

Time vs N → shows MergeSort & QuickSort as n log n, Select close to linear.
Max recursion depth vs N → matches log n for MergeSort, QuickSort, Closest Pair.
Constant factors → cache effects, insertion sort cutoff, in-place partitions.
Time vs N
Recursion Depth vs N

## Testing

Sorting: tested on random and adversarial arrays. Checked recursion depth (QS depth ≤ 2*log₂(n)).
Select: compared with Arrays.sort result over 100 random trials.
Closest Pair: checked against brute-force O(n²) for small n (≤ 2000).
Notes on Design
Always recurse on smaller partition → safe stack depth.
Reuse buffer for MergeSort → less memory.
Insertion sort cutoff helps on small inputs.
Strip method in Closest Pair reduces comparisons.
CLI writes CSV for later plotting.
