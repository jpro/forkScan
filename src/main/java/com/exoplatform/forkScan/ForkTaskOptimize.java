package com.exoplatform.forkScan;


import java.io.File;

public class ForkTaskOptimize extends ForkTask {
    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTaskOptimize(String searchPath) {
        super(searchPath);
    }

    /**
     * Give current path and count nested files and directories
     */
    protected void init() {
        super.init();
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if (currentFile.isDirectory() && !isLink(currentFile)) {
                    localDirectoryCount++;
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
