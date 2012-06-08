package com.exoplatform.forkAndJoin;

import jsr166y.ForkJoinPool;

/**
 * Created with IntelliJ IDEA.
 * User: dozie
 * Date: 08.06.12
 * Time: 10:02
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar fork.jar \"path\" \"nested level\"\nFor example: java -jar fork.jar /usr 2");
            System.exit(-1);
        }

        String path = "";
        int level = 0;

        try {
            path = args[0];
            level = Integer.parseInt(args[1]);
        } catch (IndexOutOfBoundsException e2) {
            System.out.println("Wrong arguments.");
            System.exit(-1);
        } catch (NumberFormatException e1) {
            System.out.println("Wrong level.");
            System.exit(-1);
        }

        ForkJoinPool fjPool = new ForkJoinPool();
        fjPool.invoke(new ForkTask(path, level));

    }
}