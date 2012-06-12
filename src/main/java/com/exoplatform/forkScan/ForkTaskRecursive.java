package com.exoplatform.forkScan;


import java.io.File;

public class ForkTaskRecursive extends ForkTask {
    /**
     * Attempt to get list of files in search path
     * @param searchPath - path for get list of nested files
     */
    ForkTaskRecursive(String searchPath) {
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
                ForkTask task = null;
                if(!isLink(currentFile)) {
                    stat.addSummaryFilesSize(currentFile.length());
                }
                if (!isLink(currentFile) && currentFile.isDirectory()) {
                   task = new ForkTaskOptimize(currentFile.getAbsolutePath());
                   stat.add(task.compute());
                }
            }
        }
        stat.setControlEndTime(System.currentTimeMillis());

        return stat;
    }
}
