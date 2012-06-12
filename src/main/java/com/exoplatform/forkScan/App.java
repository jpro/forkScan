package com.exoplatform.forkScan;

import jsr166y.ForkJoinPool;

/**
 * Main class that contains a entry point and start each algorithm for same path to view different between
 * implementations of various algorithms.
 */
public class App {
    private static String path = "";
    private static int threadCount = 0;

    /**
     * Main method
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar forkScan.jar \"path\" \"thread count\"\nThread count must be in range of 1 and 100");
            System.exit(-1);
        }

        try {
            path = args[0];
            threadCount = Integer.parseInt(args[1]);
            if (threadCount <= 0 || threadCount > 100) throw new IllegalArgumentException("Threads count is abnormal.");
        } catch (IndexOutOfBoundsException ex1) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        } catch (NumberFormatException ex2) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        } catch (IllegalArgumentException ex3) {
            System.out.println(ex3.getMessage());
            System.exit(-1);
        }

        App app = new App();

        app.startRecursive(1);
        app.startThread(threadCount);
        app.startOptimized(threadCount);
    }

    /**
     * Start method. Run task with given parallelism level.
     * @param threadCount - parallelism level.
     */
    private void startOptimized(int threadCount) {
        printStatistic(new ForkJoinPool(threadCount).invoke(new ForkTaskOptimize(path)), "Threaded but optimized", threadCount);
    }

    /**
     * Start method. Run task with given parallelism level.
     * @param threadCount - parallelism level.
     */
    private void startRecursive(int threadCount) {
        printStatistic(new ForkJoinPool(threadCount).invoke(new ForkTaskRecursive(path)), "Fully recursive (1 thread)", threadCount);
    }

    /**
     * Start method. Run task with given parallelism level.
     * @param threadCount - parallelism level.
     */
    private void startThread(int threadCount) {
        printStatistic(new ForkJoinPool(threadCount).invoke(new ForkTaskThread(path)), "Fully threaded", threadCount);
    }

    /**
     * Print result of statistic
     * @param stat - statistic object from task
     * @param typeOfAlgorithm - name of algorithm
     */
    private void printStatistic(Statistic stat, String typeOfAlgorithm, int threads) {
        stat.setPath(path);
        stat.setThreads(threads);
        System.out.println(typeOfAlgorithm + " algorithm");
        System.out.println(stat.toString());
    }
}