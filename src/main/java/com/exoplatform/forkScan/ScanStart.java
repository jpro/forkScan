package com.exoplatform.forkScan;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Main class that contains a entry point and start each algorithm for same path to view different between
 * implementations of various algorithms.
 */
public class ScanStart {
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

        if (args.length == 0) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        }
        path = args[0];

        String[] algorithms = new String[]{
                "com.exoplatform.forkScan.ScanRecursive",
                "com.exoplatform.forkScan.ScanThread",
                "com.exoplatform.forkScan.ScanOptimize"
        };

        try {
            for (String currentAlgorithm: algorithms) {
                Class<? extends Scanable> cl = Class.forName(currentAlgorithm).asSubclass(Scanable.class);
                Constructor<? extends Scanable> constructor = cl.getDeclaredConstructor();
                Scanable scan = constructor.newInstance();
                System.out.println(scan.getStat(path));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

