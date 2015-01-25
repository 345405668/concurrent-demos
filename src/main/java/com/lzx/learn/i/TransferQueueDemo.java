package com.lzx.learn.i;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/** java 7
 * Created by luozhixin on 15/1/25.
 * http://download.oracle.com/javase/7/docs/api/java/util/concurrent/TransferQueue.html
 * http://ifeve.com/java-transfer-queue/
 * Java7中加入了JSR 166y规范对集合类和并发类库的改进。其中的一项是增加了接口TransferQueue和其实现类LinkedTransferQueue。
 * TransferQueue继承了BlockingQueue（BlockingQueue又继承了Queue）并扩展了一些新方法。
 * BlockingQueue（和Queue）是Java 5中加入的接口，它是指这样的一个队列：当生产者向队列添加元素但队列已满时，生产者会被阻塞；当消费者从队列移除元素但队列为空时，消费者会被阻塞。
 * TransferQueue则更进一步，生产者会一直阻塞直到所添加到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）。新添加的transfer方法用来实现这种约束。
 * 顾名思义，阻塞就是发生在元素从一个线程transfer到另一个线程的过程中，它有效地实现了元素在线程之间的传递（以建立Java内存模型中的happens-before关系的方式）。
 * TransferQueue还包括了其他的一些方法：两个tryTransfer方法，一个是非阻塞的，另一个带有timeout参数设置超时时间的。还有两个辅助方法hasWaitingConsumer()和getWaitingConsumerCount()。
 * 两个线程之间传递元素特别有用
 */
public class TransferQueueDemo {
    public static void main(String[] a) throws InterruptedException {
        final TransferQueue<String> transferQueue = new LinkedTransferQueue<String>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            public void run() {
                try {
                    // 此处阻塞，等待take()，poll()的发生。
                    transferQueue.transfer("test"); //如果此处注释掉，会阻塞在下面take的方法调用上
                    System.out.println("子线程完成传递.");
                } catch (Exception e) {}
            }
        });

        // 此处阻塞，等待trankser(当然可以是别的插入元素的方法)的发生
        String test = transferQueue.take();
        System.out.printf("主线程完成获取 %s.\n", test);
        Thread.sleep(1000);
    }


}
