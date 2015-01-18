package com.lzx.learn.i.forkjoin_java7.ForkJoinWorkerThreadFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * Created by luozhixin on 15/1/15.
 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ForkJoinPool.ForkJoinWorkerThreadFactory.html
 */
public class MyWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new MyWorkerThread(pool);
    }
}
