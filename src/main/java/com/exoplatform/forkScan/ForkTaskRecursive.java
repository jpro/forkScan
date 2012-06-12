package com.exoplatform.forkScan;


import java.io.File;

/**
 * Class that allow to calculate files size recursively in one thread.
 */
public class ForkTaskRecursive extends ForkTask {
    /**
     * Attempt to get list of files in search path.
     * @param searchPath - path for get list of nested files
     */
    ForkTaskRecursive(String searchPath) {
        super(searchPath);
    }

    /**
     * Give current path and count nested files and directories
     */
    protected void init() {
        super.init();
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if(!isLink(currentFile)) {
                    stat.addSummaryFilesSize(currentFile.length());
                }
            }
        }
    }

    /**
     * Read nested files and directories in recursive form used one thread.
     */
    protected Statistic compute() {
        init();
        for (File currentFile: listFiles) {
            stat.add(localRecursive(currentFile));
        }
        stat.setControlEndTime(System.currentTimeMillis());
        return stat;
    }

    /**
     * Function that recursively view path to search files and directories and then calculate files size and return
     * Statistic object
     * @param directory - current directory
     * @return - Statistic object
     */
    private Statistic localRecursive(File directory) {
        File[] listFiles = directory.listFiles();
        Statistic localStat = new Statistic();
        if (listFiles != null) {
            for(File currentFile: listFiles) {
                if(!isLink(currentFile)) {
                    localStat.addSummaryFilesSize(currentFile.length());
                }
                if (!isLink(currentFile) && currentFile.isDirectory()) {
                    localStat.incDirectoriesCount();
                    localStat.add(localRecursive(currentFile));
                } else if(currentFile.isFile() && !isLink(currentFile)) {
                    localStat.incFilesCount();
                }
            }
        }
        return localStat;
    }
}
