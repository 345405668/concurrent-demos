package com.lzx.learn.i.blocking.domain;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by luozhixin on 14-9-12.
 * 使用PriorityBlockingQueue进行任务按优先级同步执行
 */
public class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> q;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q)
    {
        this.q = q;
    }

    @Override
    public void run() {
        try
        {
            while (!Thread.interrupted())
            {
                q.take().run();
            }
        } catch (InterruptedException e)
        {
        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}
