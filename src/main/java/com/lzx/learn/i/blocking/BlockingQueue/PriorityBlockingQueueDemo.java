package com.lzx.learn.i.blocking.BlockingQueue;

import com.lzx.learn.i.blocking.domain.PrioritizedTaskConsumer;
import com.lzx.learn.i.blocking.domain.PrioritizedTaskProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by luozhixin on 14-9-12.
 * 类似于LinkedBlockQueue,但其所含对象的排序不是FIFO,而是依据对象的自然排序顺序或者是构造函数的Comparator决定的顺序.
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/PriorityBlockingQueue.html
 */
public class PriorityBlockingQueueDemo {
    public static void main(String args[])
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        //PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
        exec.execute(new PrioritizedTaskProducer(queue, exec));
        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
        }
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
}
