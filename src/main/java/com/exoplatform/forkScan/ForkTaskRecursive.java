package com.exoplatform.forkScan;


import jsr166y.ForkJoinPool;

import java.io.File;

/**
 * Class that allow to calculate files size recursively in one thread.
 */
public class ForkTaskRecursive extends ForkTask {

    /**
     * Current path in which we must start viewing the file system for calculations
     */
    private File currentSearchPath;

    /**
     * Attempt to get list of files in search path.
     * @param searchPath - path for get list of nested files
     */
    ForkTaskRecursive(String searchPath) {
        currentSearchPath = new File(searchPath);
    }

    ForkTaskRecursive() {

    }

    /**
     * Read nested files and directories in recursive form used one thread.
     */
    public Statistic compute() {
        stat.add(localRecursiveSearch(currentSearchPath));
        stat.setControlEndTime(System.currentTimeMillis());
        return stat;
    }

    /**
     * Function that recursively view path to search files and directories and then calculate files size and return
     * Statistic object
     * @param directory - current directory
     * @return - Statistic object
     */
    private Statistic localRecursiveSearch(File directory) {
        File[] listFiles = directory.listFiles();
        Statistic localStat = new Statistic();
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if(!isLink(currentFile)) {
                    localStat.addSummaryFilesSize(currentFile.length());
                }
                if (!isLink(currentFile) && currentFile.isDirectory()) {
                    localStat.incDirectoriesCount();
                    localStat.add(localRecursiveSearch(currentFile));
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    localStat.incFilesCount();
                }
            }
        }
        return localStat;
    }

    public Statistic getStat(String path, int threadCount) {
        return new ForkJoinPool(threadCount).invoke(new ForkTaskRecursive(path));
    }
}
