package com.exoplatform.forkScan;

/**
 * Interface that define contract which classes that implements this interface must be implement method getStat.
 * This method retrieve statistic for algorithm of calculation files size and files/directories count.
 */
public interface Scanable {

    /**
     * Retrieve statistic of work algorithm
     * @param path - path which must be viewed
     * @return - object of statistic
     */
    public Statistic getStat(String path);
}
