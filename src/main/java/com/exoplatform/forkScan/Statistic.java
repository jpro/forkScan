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

    /**
     * Constructor that receives start time in milliseconds for calculate how long algorithm work
     * @param startTime - current time in milliseconds
     */
    public Statistic(long startTime) {
        this.controlStartTime.set(startTime);
    }

    /**
     * Default constructor
     */
    public Statistic() {}

    /**
     * Summarize two objects of statistic for store general stats value
     * @param otherStat - other input object of statistic
     */
    public void add(Statistic otherStat) {
        filesCount.getAndAdd(otherStat.filesCount.get());
        directoriesCount.getAndAdd(otherStat.directoriesCount.get());
        summaryFilesSize.getAndAdd(otherStat.summaryFilesSize.get());
        controlEndTime.set(otherStat.controlEndTime.get());
    }

    /**
     * Increments files count
     */
    public void incFilesCount() {
        filesCount.getAndIncrement();
    }

    /**
     * Increments directories count
     */
    public void incDirectoriesCount() {
        directoriesCount.getAndIncrement();
    }

    /**
     * Return directories count for optimized algorithm
     * @return - directories count
     */
    public int getDirectoriesCount() {
        return directoriesCount.get();
    }

    /**
     * Add to current value of files size new value of current file
     * @param size - size of current file
     */
    public void addSummaryFilesSize(long size) {
        summaryFilesSize.getAndAdd(size);
    }

    /**
     * Set control end time for view total time of work
     * @param time - value of time after work method compute()
     */
    public void setControlEndTime(long time) {
        controlEndTime.set(time);
    }

    /**
     * Human readable format of bytes
     * @param bytes - input size in bytes
     * @return - string which consist of readable format of size
     */
    private String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Set path value
     * @param path - value of search path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Set value of thread count
     * @param threads - count of threads
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }

    /**
     * toString() method that print statistic in table view
     * @return - result of statistic in table view
     */
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
