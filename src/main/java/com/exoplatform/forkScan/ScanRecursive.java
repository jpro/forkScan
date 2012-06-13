package com.exoplatform.forkScan;

import jsr166y.ForkJoinPool;
import java.io.File;

/**
 * Class that allow to calculate files size recursively in one thread.
 */
public final class ScanRecursive extends ScanTask {

    /**
     * Current path in which we must start viewing the file system for calculations
     */
    private File currentSearchPath;

    /**
     * Attempt to get list of files in search path.
     * @param searchPath - path for get list of nested files
     */
    ScanRecursive(String searchPath) {
        currentSearchPath = new File(searchPath);
    }

    /**
     * Default constructor
     */
    ScanRecursive() {}

    /**
     * Read nested files and directories in recursive form used one thread.
     */
    protected Statistic compute() {
        innerStatistic.add(recursiveFileSearch(currentSearchPath));
        innerStatistic.setControlEndTime(System.currentTimeMillis());
        return innerStatistic;
    }

    /**
     * Function that recursively view path to search files and directories and then calculate files size and return
     * Statistic object
     * @param directory - current directory
     * @return - Statistic object
     */
    private Statistic recursiveFileSearch(File directory) {
        File[] listFiles = directory.listFiles();
        Statistic localStat = new Statistic();
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    localStat.incDirectoriesCount();
                    localStat.add(recursiveFileSearch(currentFile));
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    localStat.incFilesCount();
                    localStat.addSummaryFilesSize(currentFile.length());
                }
            }
        }
        return localStat;
    }

    /**
     * Implemented method that return statistic object of work algorithm.
     * @param path - path which must be viewed
     * @return - object statistic.
     */
    public Statistic getStat(String path) {
        Statistic resultStatistic = (new ForkJoinPool(THREAD_COUNT_RECURSIVE).invoke(new ScanRecursive(path)));

        resultStatistic.setPath(path);
        resultStatistic.setThreads(THREAD_COUNT_RECURSIVE);

        return resultStatistic;
    }
}
