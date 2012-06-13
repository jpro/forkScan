package com.exoplatform.forkScan;

/**
 * Interface that define contract which classes that implements this interface must be implement method getStat.
 * This method retrieve statistic for algorithm of calculation files size and files/directories count.
 */
public interface Scan {

    /**
     * Retrieve statistic of work algorithm
     * @param path - path which must be viewed
     * @return - object of statistic
     */
    public Statistic getStat(String path);

    /**
     * Defined thread count
     */
    public int THREAD_COUNT = 2;

    /**
     * Define one thread to recursive algorithm
     */
    public int THREAD_COUNT_RECURSIVE = 1;
}
