package com.exoplatform.forkScan;

public interface Scan {
    public Statistic getStat(String path, int threadCount);
}
