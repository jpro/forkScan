package com.exoplatform.forkScan;


import jsr166y.ForkJoinPool;

import java.io.File;

/**
 * Class that allow to calculate files size, directories and files count without optimizations only on threads.
 * Each viewed directory is a separate task that adds to collection. Collection forks and algorithm find own
 * list of directories for each task. Then algorithm performed for to each task.
 */
public class ForkTaskThread extends ForkTask {

    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTaskThread(String searchPath) {
        File file = new File(searchPath);
        listFiles = file.listFiles();
    }

    ForkTaskThread() {

    }

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
    public Statistic getStat() {
        return stat;
    }
    /**
     * Read nested files and directories. If nested directories are over 10,
     * then reading is performed by multitasking otherwise in one task.
     */
    public Statistic compute() {
        init();
        if (listFiles != null) {
                for(File currentFile: listFiles) {
                    if (!isLink(currentFile) && currentFile.isDirectory()) {
                        taskList.add(new ForkTaskOptimize(currentFile.getAbsolutePath()));
                    }
                    if(!isLink(currentFile)) {
                        stat.addSummaryFilesSize(currentFile.length());
                    }
                }

                invokeAll(taskList);
                for (ForkTask currentTask : taskList) {
                    stat.add(currentTask.join());
                }
        }
        stat.setControlEndTime(System.currentTimeMillis());

        return stat;
    }

    public Statistic getStat(String path, int threadCount) {
        return new ForkJoinPool(threadCount).invoke(new ForkTaskThread(path));
    }
}
