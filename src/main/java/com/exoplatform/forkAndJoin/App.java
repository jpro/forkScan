package com.exoplatform.forkAndJoin;

import jsr166y.ForkJoinPool;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    private static String path = "";

    /**
     * Main method
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar fork.jar \"path\"");
            System.exit(-1);
        }

        try {
            path = args[0];
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        }

        App application = new App();

        application.start(-1);
        application.start(1);
        application.start(2);
        application.start(4);
        application.start(8);
        application.start(16);
        application.start(32);
    }

    /**
     * Start method. Run task with given parallelism level.
     * @param parallelismLevel - parallelism level. If level is -1, that parallelism level set to default of the system.
     */
    public void start(int parallelismLevel) {
        ForkJoinPool forkJoinPool;

        Statistic.getInstance().setControlStartTime(System.currentTimeMillis());

        System.out.println("Start: " + getTime());
        System.out.println("Path: " + path);
        System.out.println("Parallelism level: " + ((parallelismLevel == -1)?"default":parallelismLevel));

        if (parallelismLevel == -1) {
            forkJoinPool = new ForkJoinPool();
        } else {
            forkJoinPool = new ForkJoinPool(parallelismLevel);
        }
        forkJoinPool.invoke(new ForkTask(path));

        System.out.println(Statistic.getInstance().toString());
        System.out.println("End: " + getTime() + "\n\n");

        Statistic.getInstance().cleanStatistic();
    }

    /**
     * Get formatted current time
     * @return Human time format
     */
    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}