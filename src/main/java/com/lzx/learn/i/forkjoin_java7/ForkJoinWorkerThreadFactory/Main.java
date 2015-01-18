package com.lzx.learn.i.forkjoin_java7.ForkJoinWorkerThreadFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Created by luozhixin on 15/1/15.
 */
public class Main {
    public static void main(String[] args) {
        MyWorkerThreadFactory factory=new MyWorkerThreadFactory();
        ForkJoinPool pool=new ForkJoinPool(4, factory, null, false);
        int array[]=new int[100000];
        for (int i=0; i<array.length; i++){
            array[i]=1;
        }
        MyRecursiveTask task=new MyRecursiveTask(array,0,array.length);
        pool.execute(task);
        task.join();
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
            System.out.printf("Main: Result: %d\n",task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: End of the program\n");

    }
}
