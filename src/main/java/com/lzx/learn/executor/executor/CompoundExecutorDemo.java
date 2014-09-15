package com.lzx.learn.executor.executor;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

/**
 * Created by luozhixin on 14-9-1.
 * Executor 实现一般都对调度任务的方式和时间强加了某种限制。以下执行程序使任务提交与第二个执行程序保持连续，这说明了一个复合执行程序
 */
public class CompoundExecutorDemo implements Executor {

    final Queue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
    final Executor executor;
    Runnable active;

    CompoundExecutorDemo(Executor executor) {
        this.executor = executor;
    }

    @Override
    public synchronized void execute(final Runnable command) {
        tasks.offer(new Runnable() {
            public void run() {
                try {
                    command.run();
                } finally {
                    scheduleNext();
                }
            }
        });//insert one task
        if (active == null) {
            scheduleNext();
        }
    }

    protected synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }

    public static void main(String[] args) {
        ExecutorImplClassDemo executorImplClassDemo = new ExecutorImplClassDemo();
        CompoundExecutorDemo compoundExecutorDemo = new CompoundExecutorDemo(executorImplClassDemo);
        ExecutorImplClassDemo.RunnableImpl runnable = new ExecutorImplClassDemo.RunnableImpl();
        compoundExecutorDemo.execute(runnable);
        try {
            sleep(3000);//等程序执行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main print:" +  runnable.getCount());
    }
}
