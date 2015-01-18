package com.lzx.learn.i;

import java.util.concurrent.*;

/**
 * Created by luozhixin on 15/1/18.
 * RejectedExecutionHandler http://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/RejectedExecutionHandler.html
 * ThreadPoolExecutor http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/ThreadPoolExecutor.html
 * 当 Executor 已经关闭，并且 Executor 将有限边界用于最大线程和工作队列容量，且已经饱和时，在方法 execute(java.lang.Runnable) 中提交的新任务将被拒绝。
 * 在以上两种情况下，execute 方法都将调用其 RejectedExecutionHandler 的 RejectedExecutionHandler.rejectedExecution(java.lang.Runnable, java.util.concurrent.ThreadPoolExecutor) 方法
 *
 * 1.  pool threads启动后，以后的任务获取都会通过block queue中，获取堆积的runnable task.
 * 所以建议：  block size >= corePoolSize ，不然线程池就没任何意义
 * 2.  corePoolSize 和 maximumPoolSize的区别， 和大家正常理解的数据库连接池不太一样。
 *  据dbcp pool为例，会有minIdle , maxActive配置。minIdle代表是常驻内存中的threads数量，maxActive代表是工作的最大线程数。   *  这里的corePoolSize就是连接池的maxActive的概念，它没有minIdle的概念(每个线程可以设置keepAliveTime，超过多少时间多有任务后销毁线程，但不会固定保持一定数量的threads)。    * 这里的maximumPoolSize，是一种救急措施的第一层。当threadPoolExecutor的工作threads存在满负荷，并且block queue队列也满了，这时代表接近崩溃边缘。这时允许临时起一批threads，用来处理runnable，处理完后立马退出。
    所以建议：   maximumPoolSize >= corePoolSize =期望的最大线程数。 (我曾经配置了corePoolSize=1, maximumPoolSize=20, blockqueue为无界队列，最后就成了单线程工作的pool。典型的配置错误)
 3. 善用blockqueue和reject组合.

 */
public class RejectedExecutionHandlerDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("r1 running");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r2 running");
            }
        };

        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                System.out.println("r3 running");
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,2, TimeUnit.SECONDS,workQueue);
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r.toString() + " rejected!sleep 3 sec and than run again");
                try {
                    Thread.sleep(3000);
                    executor.execute(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        executor.execute(runnable);
        executor.execute(r2);
        executor.execute(r3);
        executor.shutdown();
    }
}
