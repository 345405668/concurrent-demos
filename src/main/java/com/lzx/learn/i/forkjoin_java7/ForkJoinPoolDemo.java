package com.lzx.learn.i.forkjoin_java7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by luozhixin on 15/1/14.
 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ForkJoinPool.html
 * ForkJoinPool提供了一系列的submit方法，计算任务。ForkJoinPool默认的线程数通过Runtime.availableProcessors()获得，因为在计算密集型的任务中，获得多于处理性核心数的线程并不能获得更多性能提升。
 */
public class ForkJoinPoolDemo {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Future<Integer> result = forkJoinPool.submit(new Calculator(0, 10000));

        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
