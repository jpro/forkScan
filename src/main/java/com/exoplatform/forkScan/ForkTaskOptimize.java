package com.exoplatform.forkScan;


import java.io.File;

/**
 * Class that allow to calculate files size, directories and files count with optimizations.
 * First, algorithm view current path. If nested directories are over 10, then algorithm
 * create tasks on this directories and then forks them. If nested directories in forked task are over 10 again -
 * algorithm start optimization again. Else if nested directories less then 10 algorithm doesn't create separate
 * tasks and calculate files/directories count and size directly.
 */
public class ForkTaskOptimize extends ForkTask {
    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTaskOptimize(String searchPath) {
        File file = new File(searchPath);
        listFiles = file.listFiles();
    }

    /**
     * Give current path and count nested files and directories
     */
    protected void init() {
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    localDirectoryCount++;
                    stat.incDirectoriesCount();
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    stat.incFilesCount();
                }
            }
        }
    }

    /**
     * Read nested files and directories. If nested directories are over 10,
     * then reading is performed by multitasking otherwise in one task.
     */
    protected Statistic compute() {
        init();
        if (listFiles != null) {
            if (localDirectoryCount <= 10) {
                for(File currentFile: listFiles) {
                    ForkTask task = null;
                    if(!isLink(currentFile)) {
                        stat.addSummaryFilesSize(currentFile.length());
                    }
                    if (!isLink(currentFile) && currentFile.isDirectory()) {
                        task = new ForkTaskOptimize(currentFile.getAbsolutePath());

                        stat.add(task.compute());
                    }
                }
            } else {
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
        }
        stat.setControlEndTime(System.currentTimeMillis());

        return stat;
    }
}
