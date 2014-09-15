package com.lzx.learn.blocking.BlockingQueue;

import java.util.concurrent.*;

/**
 * Created by luozhixin on 14-9-12.
 * 一种无缓冲的等待队列，类似于无中介的直接交易，有点像原始社会中的生产者和消费者，生产者拿着产品去集市销售给产品的最终消费者，而消费者必须亲自去集市找到所要商品的直接生产者，如果一方没有找到合适的目标，那么对不起，大家都在集市等待。相对于有缓冲的BlockingQueue来说，少了一个中间经销商的环节（缓冲区），如果有经销商，生产者直接把产品批发给经销商，而无需在意经销商最终会将这些产品卖给那些消费者，由于经销商可以库存一部分商品，因此相对于直接交易模式，总体来说采用中间经销商的模式会吞吐量高一些（可以批量买卖）；但另一方面，又因为经销商的引入，使得产品从生产者到消费者中间增加了额外的交易环节，单个产品的及时响应性能可能会降低。
 * 声明一个SynchronousQueue有两种不同的方式，它们之间有着不太一样的行为。公平模式和非公平模式的区别:
 *　　如果采用公平模式：SynchronousQueue会采用公平锁，并配合一个FIFO队列来阻塞多余的生产者和消费者，从而体系整体的公平策略；
 *　　但如果是非公平模式（SynchronousQueue默认）：SynchronousQueue采用非公平锁，同时配合一个LIFO队列来管理多余的生产者和消费者，而后一种模式，如果生产者和消费者的处理速度有差距，则很容易出现饥渴的情况，即可能有某些生产者或者是消费者的数据永远都得不到处理。
 * 该队列的特点
 * 1.容量为0，无论何时 size方法总是返回0
 * 2.put操作阻塞，直到另外一个线程取走队列的元素。
 * 3.take操作阻塞，直到另外的线程put某个元素到队列中。
 * 4.任何线程只能取得其他线程put进去的元素，而不会取到自己put进去的元素
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/SynchronousQueue.html
 */
public class SynchronousQueueDemo {
    private static boolean running = true ;
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();


        new Thread() {
            @Override
            public void run() {
                try {
                    while (running) {
                        System.out.println("size1:" + queue.size());
                        sleep(1000);
                        //System.out.println("element:" + queue.take());
                        //System.out.println("size2:" + queue.size());
                    }
                } catch (/*Interrupted*/Exception e) {

                    e.printStackTrace();
                }
            }
        }.start();


        queue.put(1);
        queue.put(2);
        queue.put(3);
        //*4
        ScheduledExecutorService service= Executors.newScheduledThreadPool(1);
        ScheduledFuture future2=service.schedule(new Callable()
        {
            public String call() throws InterruptedException {
                running = false;
                return "taskcancelled!";
            }
        },5, TimeUnit.SECONDS);
        System.out.println(future2.get());
    }

}
