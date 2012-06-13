package com.exoplatform.forkScan;

import jsr166y.ForkJoinPool;
import java.io.File;

/**
 * Class that allow to calculate files size, directories and files count with optimizations.
 * First, algorithm view current path. If nested directories are over 10, then algorithm
 * create tasks on this directories and then forks them. If nested directories in forked task are over 10 again -
 * algorithm start optimization again. Else if nested directories less then 10 algorithm doesn't create separate
 * tasks and calculate files/directories count and size directly.
 */
public final class ScanOptimize extends ScanTask {

    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ScanOptimize(String searchPath) {
        File file = new File(searchPath);
        localFileList = file.listFiles();
    }

    /**
     * Default constructor
     */
    ScanOptimize() {}

    /**
     * Give current path and count nested files and directories.
     */
    private void countFilesAndDirectories() {
        if (localFileList != null) {
            for(File currentFile: localFileList) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    innerStatistic.incDirectoriesCount();
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    innerStatistic.incFilesCount();
                }
            }
        }
    }

    /**
     * Read nested files and directories. If nested directories are over 10,
     * then reading is performed by multitasking otherwise in one task.
     */
    protected Statistic compute() {
        countFilesAndDirectories();
        if (localFileList != null) {
            if (innerStatistic.getDirectoriesCount() <= 10) {
                for(File currentFile: localFileList) {
                    if (currentFile.isDirectory() && !isLink(currentFile)) {
                        ScanTask scanTask = new ScanOptimize(currentFile.getAbsolutePath());

                        innerStatistic.add(scanTask.compute());
                    } else if(currentFile.isFile() && !isLink(currentFile)) {
                        innerStatistic.addSummaryFilesSize(currentFile.length());
                    }
                }
            } else {
                for(File currentFile: localFileList) {
                    if (currentFile.isDirectory() && !isLink(currentFile)) {
                        scanTaskList.add(new ScanOptimize(currentFile.getAbsolutePath()));
                    } else if(currentFile.isFile() && !isLink(currentFile)) {
                        innerStatistic.addSummaryFilesSize(currentFile.length());
                    }
                }

                invokeAll(scanTaskList);
                for (ScanTask currentScanTask : scanTaskList) {
                    innerStatistic.add(currentScanTask.join());
                }
            }
        }
        innerStatistic.setControlEndTime(System.currentTimeMillis());

        return innerStatistic;
    }

    /**
     * Implemented method that return statistic object of work algorithm.
     * @param path - path which must be viewed
     * @return - object statistic.
     */
    public Statistic getStat(String path) {
        Statistic resultStatistic = (new ForkJoinPool(THREAD_COUNT).invoke(new ScanOptimize(path)));

        resultStatistic.setPath(path);
        resultStatistic.setThreads(THREAD_COUNT);
        resultStatistic.setAlgorithmType("Optimized algorithm:");

        return resultStatistic;
    }
}
