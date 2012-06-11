package com.exoplatform.forkAndJoin;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class for keeping statistic for compute() method
 */
class Statistic {
    private AtomicInteger directoriesCount;
    private AtomicInteger filesCount;
    private AtomicLong summaryFilesSize;
    private AtomicLong controlStartTime;
    private AtomicLong controlEndTime;
    private static Statistic instance = new Statistic();

    Statistic() {
        directoriesCount = new AtomicInteger(0);
        filesCount = new AtomicInteger(0);
        summaryFilesSize = new AtomicLong(0);
        controlStartTime = new AtomicLong(0);
        controlEndTime = new AtomicLong(0);
    }

    public void setDirectoriesCount(int count) {
        directoriesCount.set(count);

    }

    public int getDirectoriesCount() {
        return directoriesCount.get();
    }

    public void setFilesCount(int count) {
        filesCount.set(count);
    }

    public int getFilesCount() {
        return filesCount.get();
    }

    public void setSummaryFilesSize(long size) {
        summaryFilesSize.set(size);
    }

    public long getSummaryFilesSize() {
        return summaryFilesSize.get();
    }

    public long getControlStartTime() {
        return controlStartTime.get();
    }

    public void setControlStartTime(long controlTime) {
        controlStartTime.set(controlTime);
    }

    public long getControlEndTime() {
        return controlEndTime.get();
    }

    public void setControlEndTime(long controlTime) {
        controlEndTime.set(controlTime);
    }

    public synchronized static Statistic getInstance() {
        return instance;
    }

    public void cleanStatistic() {
        directoriesCount.set(0);
        filesCount.set(0);
        summaryFilesSize.set(0);
        controlStartTime.set(0);
        controlEndTime.set(0);
    }

    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public String toString() {
        return  "Directories count: " + getDirectoriesCount() +
                "\nFiles count: " + getFilesCount() +
                "\nSummary file size: " + getSummaryFilesSize() + " bytes " + humanReadableByteCount(getSummaryFilesSize()) +
                "\nUsed time: " + (getControlEndTime() - getControlStartTime()) + " milliseconds";
    }
}