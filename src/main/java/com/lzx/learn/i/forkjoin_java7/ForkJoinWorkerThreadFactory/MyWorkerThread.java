package com.lzx.learn.i.forkjoin_java7.ForkJoinWorkerThreadFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * Created by luozhixin on 15/1/14.
 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ForkJoinWorkerThread.html
 */
public class MyWorkerThread extends ForkJoinWorkerThread {
    private static ThreadLocal<Integer> taskCounter=new ThreadLocal<Integer>();

    protected MyWorkerThread(ForkJoinPool pool) {
        super(pool);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.printf("MyWorkerThread %d: Initializing taskcounter.\n",getId());
        taskCounter.set(0);
    }

    @Override
    protected void onTermination(Throwable exception) {
        System.out.printf("MyWorkerThread %d:%d\n",getId(),taskCounter.get());
        super.onTermination(exception);
    }

    public void addTask(){
        int counter=taskCounter.get().intValue();
        counter++;
        taskCounter.set(counter);
    }

    public Integer getTaskCount(){
        return taskCounter.get().intValue();
    }

}
