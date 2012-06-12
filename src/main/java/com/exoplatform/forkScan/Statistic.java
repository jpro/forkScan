package com.exoplatform.forkScan;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class for keeping statistic of working program
 */
public class Statistic {
    private AtomicInteger directoriesCount = new AtomicInteger(0);
    private AtomicInteger filesCount = new AtomicInteger(0);
    private AtomicLong summaryFilesSize = new AtomicLong(0);
    private AtomicLong controlEndTime = new AtomicLong(0);
    private AtomicLong controlStartTime = new AtomicLong(0);
    private String path = "";
    private int threads = 0;

    public Statistic(long startTime) {
        this.controlStartTime.set(startTime);
    }

    public void add(Statistic otherStat) {
        filesCount.getAndAdd(otherStat.filesCount.get());
        directoriesCount.getAndAdd(otherStat.directoriesCount.get());
        summaryFilesSize.getAndAdd(otherStat.summaryFilesSize.get());
        controlEndTime.set(otherStat.controlEndTime.get());
    }

    public void incFilesCount() {
        filesCount.getAndIncrement();
    }

    public void incDirectoriesCount() {
        directoriesCount.getAndIncrement();
    }

    public void addSummaryFilesSize(long size) {
        summaryFilesSize.getAndAdd(size);
    }

    public void setControlEndTime(long time) {
        controlEndTime.set(time);
    }

    private String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public String toString() {
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < 100; i++) { separator.append("-"); }
        separator.append("\n");

        StringBuilder result = new StringBuilder();
        result.append(separator.toString());
        result.append(String.format("|%-20s|%8s|%12s|%11s|%30s|%12s|\n", "Path", "Threads", "Directories", "Files", "Summary files size", "Used time"));
        result.append(separator.toString());
        result.append(String.format("|%-20s|%8d|%12d|%11d|%18db(%9s)|%10dms|\n",
                path,
                threads,
                directoriesCount.get(),
                filesCount.get(),
                summaryFilesSize.get(),
                humanReadableByteCount(summaryFilesSize.get()),
                (controlEndTime.get() - controlStartTime.get()))
        );
        result.append(separator.toString());

        return result.toString();
    }
}
