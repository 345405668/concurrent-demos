package com.lzx.learn.i.forkjoin_java7.ForkJoinWorkerThreadFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by luozhixin on 15/1/15.
 */
public class MyRecursiveTask extends RecursiveTask<Integer> {
    private int array[];
    private int start;
    private int end;

    public MyRecursiveTask(int array[],int start, int end) {
        this.array=array;
        this.start=start;
        this.end=end;
    }

    @Override
    protected Integer compute() {
        Integer ret = 0;
        MyWorkerThread thread = (MyWorkerThread) Thread.currentThread();
        thread.addTask();
        for(int i = start ; i < end;i ++){
            ret += array[i];
        }
        return ret;
    }

    private Integer addResults(MyRecursiveTask task1, MyRecursiveTask task2) {
        int value;
        try {
            value = task1.get().intValue() + task2.get().intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
            value = 0;
        } catch (ExecutionException e) {
            e.printStackTrace();
            value = 0;
        }
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

}

