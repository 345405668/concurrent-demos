package com.lzx.learn.i.executor.executor_service;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Created by luozhixin on 14-9-9.
 * 在ExecutorService的基础上，ScheduledExecutorService提供了按时间安排执行任务的功能，它提供的方法主要有：
 * schedule(task,initDelay):安排所提交的Callable或Runnable任务在initDelay指定的时间后执行。
 * scheduleAtFixedRate()：安排所提交的Runnable任务按指定的间隔重复执行
 * scheduleWithFixedDelay()：安排所提交的Runnable任务在每次执行完后，等待delay所指定的时间后重复执行。
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        scheduleAtFixedRateTest();
        //scheduleWithFixedDelayTest();
    }

    public static void scheduleAtFixedRateTest() throws ExecutionException, InterruptedException {
        //*1
        ScheduledExecutorService service= Executors.newScheduledThreadPool(2);
        //*2
        Runnable task1=new Runnable()
        {
            public void run()
            {
                System.out.println("Taskrepeating.");
            }
        };
        //*3
        final ScheduledFuture future1=service.scheduleAtFixedRate(task1,0,10,TimeUnit.SECONDS);
        //*4
        ScheduledFuture future2=service.schedule(new Callable()
        {
            public String call() throws InterruptedException {
                future1.cancel(true);
                return "taskcancelled!";
            }
        },5, TimeUnit.SECONDS);
        System.out.println(future2.get());
        //*5
        service.shutdown();
    }

    public static void scheduleWithFixedDelayTest() throws ExecutionException, InterruptedException {
        //*1
        ScheduledExecutorService service= Executors.newScheduledThreadPool(2);
        //*2
        Runnable task1=new Runnable()
        {
            public void run()
            {
                System.out.println("Taskrepeating.");
            }
        };
        //*3
        final ScheduledFuture future1=service.scheduleWithFixedDelay(task1,0,10,TimeUnit.SECONDS);
        //*4
        ScheduledFuture future2=service.schedule(new Callable()
        {
            public String call() throws InterruptedException {
                future1.cancel(true);
                return "taskcancelled!";
            }
        },5, TimeUnit.SECONDS);
        System.out.println(future2.get());
        //*5
        service.shutdown();
    }
}
