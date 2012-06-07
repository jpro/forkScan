package com.exoplatform.forkAndJoin;

import jsr166y.RecursiveTask;

/**
 *
 */
public class App 
{
    public static void main(String... args)
    {
        System.out.println( "some test" );
        //new ForkJoinPool().invoke(new ValueSumCounter(10));
    }
}

class ValueSumCounter extends RecursiveTask<Integer>{
    private Integer var;

    public ValueSumCounter(Integer var) {
        this.var = var;
    }

    @Override
    protected Integer compute() {
        return var;
    }
}