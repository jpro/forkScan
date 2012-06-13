package com.exoplatform.forkScan;

import jsr166y.ForkJoinPool;
import java.io.File;

/**
 * Class that allow to calculate files size, directories and files count without optimizations only on threads.
 * Each viewed directory is a separate task that adds to collection. Collection forks and algorithm find own
 * list of directories for each task. Then algorithm performed for to each task.
 */
public final class ScanThread extends ScanTask {

    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ScanThread(String searchPath) {
        File file = new File(searchPath);
        localFileList = file.listFiles();
    }

    /**
     * Default constructor
     */
    ScanThread() {}

    /**
     * Read nested files and directories. If nested directories are over 10,
     * then reading is performed by multitasking otherwise in one task.
     */
    protected Statistic compute() {
        if (localFileList != null) {
                for(File currentFile: localFileList) {
                    if (currentFile.isDirectory() && !isLink(currentFile)) {
                        innerStatistic.incDirectoriesCount();
                        scanTaskList.add(new ScanOptimize(currentFile.getAbsolutePath()));
                    } else if(currentFile.isFile() && !isLink(currentFile)) {
                        innerStatistic.incFilesCount();
                        innerStatistic.addSummaryFilesSize(currentFile.length());
                    }
                }

                invokeAll(scanTaskList);
                for (ScanTask currentScanTask : scanTaskList) {
                    innerStatistic.add(currentScanTask.join());
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
        Statistic resultStatistic = (new ForkJoinPool(THREAD_COUNT).invoke(new ScanThread(path)));

        resultStatistic.setPath(path);
        resultStatistic.setThreads(THREAD_COUNT);
        resultStatistic.setAlgorithmType("Only on threads algorithm:");

        return resultStatistic;
    }
}
