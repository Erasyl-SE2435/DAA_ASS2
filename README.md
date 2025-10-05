Insertion Sort Implementation Project Overview
This is a Java implementation of the Insertion Sort algorithm for the Algorithmic Analysis assignment. The implementation includes necessary optimizations and a robust performance tracking system designed to generate data for empirical validation.

What's Included
Core Sorting & Metrics Classes
InsertionSort.java - Optimized Insertion Sort implementation featuring Binary Search to find the element placement position.

PerformanceTracker.java - Static class responsible for tracking all required performance metrics during sorting.

Performance Tracking
Tracks:

Execution time in nanoseconds.

Number of comparisons made.

Total array accesses (reads and writes/shifts).

Testing & Benchmarking
InsertionSortTest.java - Comprehensive Unit tests using JUnit 5 for functional correctness.

BenchmarkRunner.java - Command-line tool to automate performance testing across various input sizes and distributions.

Key Features
Optimized Insertion Sort algorithm (Binary Search for placement).

Rigorous performance metrics collection integrated into the sorting process.

Comprehensive unit testing suite covering all required edge and best/worst cases.

Command-line benchmarking tool for large-scale data collection.

Memory-efficient O(1) in-place implementation.

Algorithm Complexity (Insertion Sort)
Metric	Best Case (Ω)	Average Case (Θ)	Worst Case (O)
Time Complexity	Ω(n)	Θ(n 
2
 )	O(n 
2
 )
Space Complexity	O(1) - sorts in place		
Optimization	Binary Search for placement (Effective for best/nearly-sorted cases)		

Экспортировать в Таблицы
How to Use
The core sorting method is located in the InsertionSort class. Benchmarks are run via the BenchmarkRunner.

Basic Sorting:
Java

int[] array = {5, 2, 8, 1, 9};
InsertionSort.sort(array);
// Metrics are collected automatically during sort if configured within the class.
Run Benchmarks:
To generate the required performance_data.csv file, run the BenchmarkRunner from the command line:

Bash

# Execute the main class using the Maven exec plugin
mvn exec:java -Dexec.mainClass="cli.BenchmarkRunner"
Testing
The project includes tests for functional correctness (InsertionSortTest.java) including:

Empty arrays and single-element arrays.

Already sorted arrays (Best Case validation).

Reverse sorted arrays (Worst Case validation).

Arrays with duplicates.

Assignment Context
Pair: 1

Role: Student A (Insertion Sort)

Partner's Algorithm: Selection Sort

Task: Implement, analyze, and cross-review algorithms.

This implementation focuses on clean, optimized code, proper testing, and verifiable performance analysis required for the algorithmic analysis assignment.
