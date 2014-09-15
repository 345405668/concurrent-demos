package com.lzx.learn.executor.executor_service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luozhixin on 14-9-2.
 * CachedThreadPool 首先会按照需要创建足够多的线程来执行任务(Task)。随着程序执行的过程，有的线程执行完了任务，可以被重新循环使用时，才不再创建新的线程来执行任务。
 * FixedThreadPool 模式会使用一个优先固定数目的线程来处理若干数目的任务。规定数目的线程处理所有任务，一旦有线程处理完了任务就会被用来处理新的任务(如果有的话)。这种模式与上面的CachedThreadPool是不同的，CachedThreadPool模式下处理一定数量的任务的线程数目是不确定的。而FixedThreadPool模式下最多 的线程数目是一定的。
 * SingleThreadExecutor 模式只会创建一个线程。它和FixedThreadPool比较类似，不过线程数是一个。如果多个任务被提交给SingleThreadExecutor的话，那么这些任务会被保存在一个队列中，并且会按照任务提交的顺序，一个先执行完成再执行另外一个线程。SingleThreadExecutor模式可以保证只有一个任务会被执行。这种特点可以被用来处理共享资源的问题而不需要考虑同步的问题。
 */
public class ExecutorServiceDemo {

    static class LiftOff implements Runnable{
        protected int countDown = 10; //Default
        private static int taskCount = 0;
        private final int id = taskCount++;
        public LiftOff() {}
        public LiftOff(int countDown) {
            this.countDown = countDown;
        }
        public String status() {
            return "#" + id + "(" +
                    (countDown > 0 ? countDown : "LiftOff!") + ") ";
        }
        @Override
        public void run() {
            while(countDown-- > 0) {
                System.out.print(status());
                Thread.yield();
            }

        }
    }

    public static void main(String[] args) {
        //ExecutorService exec = Executors.newCachedThreadPool();
        //ExecutorService exec = Executors.newFixedThreadPool(3);
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 10; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
