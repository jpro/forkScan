package com.exoplatform.forkScan;


import java.io.File;

public class ForkTaskThread extends ForkTask {
    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTaskThread(String searchPath) {
        super(searchPath);
    }

    /**
     * Read nested files and directories. If nested directories are over 10,
     * then reading is performed by multitasking otherwise in one task.
     */
    protected Statistic compute() {
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
}
