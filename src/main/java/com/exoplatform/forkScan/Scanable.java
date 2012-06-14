package com.exoplatform.forkScan;

/**
 * This interface provide one method getStat() that retrieve object of statistic
 * for particular algorithm.
 */
public interface Scanable {

    /**
     * Retrieve object of statistic
     * @param args - contains search path and thread count
     * @return - object of statistic
     */
    public Statistic getStat(Object... args);
}
