package com.exoplatform.forkScan;

import jsr166y.ForkJoinPool;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        (new App()).start(threadCount);
    }

    /**
     * Start method. Run task with given parallelism level.
     * @param threadCount - parallelism level. If level is -1, that parallelism level set to default of the system.
     */
    private void start(int threadCount) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
        Statistic stat = forkJoinPool.invoke(new ForkTask(path));

        stat.setPath(path);
        stat.setThreads(threadCount);

        System.out.println(stat.toString());
    }
}