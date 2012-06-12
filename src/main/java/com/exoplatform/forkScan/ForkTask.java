package com.exoplatform.forkScan;

import jsr166y.RecursiveTask;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class, that implement algorithm for reading files and dirs from given path
 */
abstract class ForkTask extends RecursiveTask<Statistic> {

    /**
     * Create statistic for current directory
     */
    protected Statistic stat = new Statistic(System.currentTimeMillis());

    /**
     * List of files and directories in current path
     */
    protected File[] listFiles;

    /**
     * Nested directories is a future task
     */
    protected ArrayList<ForkTask> taskList = new ArrayList<ForkTask>();

    /**
     * Count nested directories
     */
    protected int localDirectoryCount = 0;

    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTask(String searchPath) {
        File file = new File(searchPath);
        listFiles = file.listFiles();
    }

    /**
     * Compute method for main computation. Must be implemented in inherited class
     * @return - object statistic
     */
    protected abstract Statistic compute();

    /**
     * Give current path and count nested files and directories
     */
    protected void init() {
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    stat.incDirectoriesCount();
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    stat.incFilesCount();
                }
            }
        }
    }

    /**
     * Check if given files is link
     * @param file - current file for check
     * @return - true if file is link and false - if file isn't link
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