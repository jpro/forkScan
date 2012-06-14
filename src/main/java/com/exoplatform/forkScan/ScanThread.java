package com.exoplatform.forkScan;

import jsr166y.ForkJoinPool;
import java.io.File;

/**
 * Class that allow to calculate summary files size, directories and files count without optimizations only on threads.
 * Each viewed directory is a separate task that adds to collection. Collection forks and algorithm find own
 * list of directories for each task. And algorithm works again on new tasks.
 */
public final class ScanThread extends ScanTask {

    public ScanThread() {}

    /**
     * Attempt to get file list in current path.
     * @param searchPath - path for get list of nested files.
     */
    ScanThread(String searchPath) {
        File file = new File(searchPath);
        localFileList = file.listFiles();
    }

    /**
     * Read nested files and directories. Every viewed directory is a future task. Then it adds to collection
     * which will be forked in future.
     * @return - Statistic object.
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
     * Method start threads that perform algorithm with no optimizations, all viewed directories are future tasks.
     * Every task run and method calculate summary size of each viewed file and also calculate count of files and
     * directories.
     * their summary files size.
     * @return - object statistic.
     */
    public Statistic getStat(Object... args) {
        String path = (String)args[0];
        threadCount = (Integer)args[1];
        Statistic resultStatistic = (new ForkJoinPool(threadCount).invoke(new ScanThread(path)));

        resultStatistic.setPath(path);
        resultStatistic.setThreads(threadCount);
        resultStatistic.setAlgorithmType("Only on threads algorithm:");

        return resultStatistic;
    }
}
