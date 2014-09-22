package com.lzx.learn.i.blocking.domain;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by luozhixin on 14-9-12.
 * 制造一系列任务,分配任务优先级
 */
public class PrioritizedTaskProducer implements Runnable{
    private Random rand = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;

    public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService e)
    {
        queue = q;
        exec = e;
    }

    @Override
    public void run() {

        for(int i = 0; i < 20; i++)
        {
            queue.add(new PrioritizedTask(rand.nextInt(10)));
            Thread.yield();
        }

        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }

            for(int i = 0; i < 10; i++)
            {
                queue.add(new PrioritizedTask(i));
            }

            queue.add(new PrioritizedTask.EndSentinel(exec));

        } catch (InterruptedException e) {

        }

        System.out.println("Finished PrioritizedTaskProducer");
    }
}


