package com.exoplatform.forkScan;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        if (args.length == 0) {
            System.out.println("Wrong argument.");
            System.exit(-1);
        }
        path = args[0];

        String[] algorithms = new String[]{
                "ScanRecursive",
                "ScanThread",
                "ScanOptimize"
        };

        try {
            Class cl = Class.forName("com.exoplatform.forkScan.Scan");
            for (String currentAlgorithm: algorithms) {
                Method m = cl.getDeclaredMethod("getStat", String.class);
                System.out.println(m.invoke(Class.forName("com.exoplatform.forkScan." + currentAlgorithm).newInstance(), path));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}