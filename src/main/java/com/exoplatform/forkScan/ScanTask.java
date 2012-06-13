package com.exoplatform.forkScan;

import jsr166y.RecursiveTask;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class, that is based for calculate count of files/directories and files size. It has abstract method compute()
 * that must be implemented in every inherited class to create own algorithm for calculate size and count of files
 * and directories.
 */
abstract class ScanTask extends RecursiveTask<Statistic> implements Scanable {

    /**
     * Define thread count.
     */
    protected int threadCount;

    /**
     * Create object of statistic for current directory which consist of files/directories count, summary files size.
     */
    protected Statistic innerStatistic = new Statistic(System.currentTimeMillis());

    /**
     * List of files and directories in current path.
     */
    protected File[] localFileList;

    /**
     * Nested directories is a future task.
     */
    protected ArrayList<ScanTask> scanTaskList = new ArrayList<ScanTask>();

    /**
     * Compute method for main computation. Must be implemented in inherited class.
     * @return - Statistic object.
     */
    protected abstract Statistic compute();

    /**
     * Check if given file is a symbolic link.
     * @param file - current file for check.
     * @return - true if file is a symbolic link else - return false.
     */
    protected boolean isLink(File file) {
        String canonicalPath = "";
        String absolutePath = "";
        try {
            canonicalPath = file.getCanonicalPath();
            absolutePath = file.getAbsolutePath();
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(-1);
        }

        return !absolutePath.equals(canonicalPath);
    }
}