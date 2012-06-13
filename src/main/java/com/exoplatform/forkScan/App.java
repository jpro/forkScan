package com.exoplatform.forkScan;

/**
 * Main class that contains a entry point and start each algorithm for same path to view different between
 * implementations of various algorithms.
 */
public class App {
    private static String path = "";

    /**
     * Main method
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar forkScan.jar \"path\"");
            System.exit(-1);
        }

        try {
            path = args[0];
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        }

        App app = new App();

        app.startRecursive();
        app.startThread();
        app.startOptimized();
    }

    /**
     * Start method. Run task with optimized calculate algorithm.
     */
    private void startOptimized() {
        Scan scan = new ScanOptimize();
        Statistic statistic = scan.getStat(path);

        System.out.println("Optimized algorithm:");
        System.out.println(statistic.toString());
    }

    /**
     * Start method. Run task with no optimizations. Task start in one thread,
     * that calculate all work in one recursive method.
     */
    private void startRecursive() {
        Scan scan = new ScanRecursive();
        Statistic statistic = scan.getStat(path);

        System.out.println("Recursive algorithm:");
        System.out.println(statistic.toString());
    }

    /**
     * Start method. Run task with no optimizations. Tasks starts only on threads.
     */
    private void startThread() {
        Scan scan = new ScanThread();
        Statistic statistic = scan.getStat(path);

        System.out.println("Only on threads algorithm:");
        System.out.println(statistic.toString());
    }
}