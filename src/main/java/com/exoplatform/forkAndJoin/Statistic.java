package com.exoplatform.forkAndJoin;

/**
 * Class for keeping statistic for compute method
 */
class Statistic {
    private int directoriesCount = 0;
    private int filesCount = 0;
    private long summaryFilesSize = 0;
    private int parallelsReading = 0;
    private int singleReading = 0;
    private long controlStartTime = 0;
    private long controlEndTime = 0;
    private static Statistic instance = new Statistic();

    public void setDirectoriesCount(int count) {
        directoriesCount = count;
    }

    public int getDirectoriesCount() {
        return directoriesCount;
    }

    public void setFilesCount(int count) {
        filesCount = count;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public void setSummaryFilesSize(long size) {
        summaryFilesSize = size;
    }

    public long getSummaryFilesSize() {
        return summaryFilesSize;
    }

    public int getParallelsReading() {
        return parallelsReading;
    }

    public void setParallelsReading(int parallelsReading) {
        this.parallelsReading = parallelsReading;
    }

    public int getSingleReading() {
        return singleReading;
    }

    public void setSingleReading(int singleReading) {
        this.singleReading = singleReading;
    }

    public long getControlStartTime() {
        return controlStartTime;
    }

    public void setControlStartTime(long controlStartTime) {
        this.controlStartTime = controlStartTime;
    }

    public long getControlEndTime() {
        return controlEndTime;
    }

    public void setControlEndTime(long controlEndTime) {
        this.controlEndTime = controlEndTime;
    }

    public synchronized static Statistic getInstance() {
        return instance;
    }

    public void cleanStatistic() {
        directoriesCount = 0;
        filesCount = 0;
        summaryFilesSize = 0;
        parallelsReading = 0;
        singleReading = 0;
        controlStartTime = 0;
        controlEndTime = 0;
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
                "\nSingle reading: " + getSingleReading() +
                "\nParallels reading: " + getParallelsReading() +
                "\nUsed time: " + (getControlEndTime() - getControlStartTime()) + " milliseconds";
    }
}