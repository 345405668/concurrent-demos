package com.lzx.learn.i;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by luozhixin on 15/1/21.
 * http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/ThreadFactory.html
 * 根据需要创建新线程的对象。使用线程工厂就无需再手工编写对 new Thread 的调用了，从而允许应用程序使用特殊的线程子类、属性等等。
 */
public class ThreadFactoryDemo {
    static class WorkThread extends Thread {

        private Runnable target;
        private AtomicInteger counter;

        public WorkThread(Runnable target, AtomicInteger counter) {
            this.target = target;
            this.counter = counter;
        }

        @Override
        public void run() {
            try {
                target.run();
            } finally {
                int c = counter.getAndDecrement();
                System.out.println("terminate no " + c + " Threads");
            }
        }
    }

    static class MyThread implements Runnable {


        public static void main(String[] args) {
            ExecutorService ctp =  Executors.newCachedThreadPool(new ThreadFactory() {
                private AtomicInteger count = new AtomicInteger();

                public Thread newThread(Runnable r) {
                    int c = count.incrementAndGet();
                    System.out.println("create no " + c + " Threads");
                    return new WorkThread(r, count);

                }
            });

            ctp.execute(new MyThread());
            ctp.execute(new MyThread());
            ctp.execute(new MyThread());
            ctp.execute(new MyThread());
            ctp.execute(new MyThread());
            ctp.execute(new MyThread());

            ctp.shutdown();
            try {
                ctp.awaitTermination(1200, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            System.out.println("complete a task!!!");
        }
    }

}
