package com.exoplatform.forkScan;

/**
 * Main class that contains a entry point. Method main() start particular algorithm based on name, which is
 * given from input arguments.
 */
public class ScanStart {

    /**
     * Entry point.
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar forkScan.jar \"path\" \"algorithm\" [thread count]\n");
            System.out.println("Algorithm must be one of this:\n----------------");
            System.out.println("1. ScanRecursive\n" + "2. ScanThread\n" + "3. ScanOptimize\n");
            System.out.println("Thread count must be in range of 1 and 100");
            System.exit(-1);
        } else if (args.length < 2) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        }

        String path = args[0];
        String algorithm = args[1];

        int threadCount = 0;
        try {
            threadCount = Integer.parseInt(args[2]);
            if (threadCount < 1 || threadCount > 101) throw new IllegalArgumentException("Abnormal threads count.");
        } catch (IndexOutOfBoundsException e) {
            threadCount = 2;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        try {
            Class<? extends Scanable> cl = Class.forName("com.exoplatform.forkScan." + algorithm).asSubclass(Scanable.class);
            Scanable scan = cl.newInstance();
            System.out.println(scan.getStat(path, threadCount));
        } catch (ClassNotFoundException e) {
            System.out.println("No such algorithm found.");
            System.exit(-1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}

