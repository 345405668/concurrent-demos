package com.lzx.learn.i.blocking.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by luozhixin on 14-9-10.
 * ArrayBlockingQueue是JAVA5中的一个阻塞队列，能够自定义队列大小，当插入时，如果队列已经没有空闲位置，那么新的插入线程将阻塞到该队列，一旦该队列有空闲位置，那么阻塞的线程将执行插入。
 * ArrayBlockingQueue在生产者放入数据和消费者获取数据，都是共用同一个锁对象，由此也意味着两者无法真正并行运行，这点尤其不同于LinkedBlockingQueue；按照实现原理来分析，ArrayBlockingQueue完全可以采用分离锁，从而实现生产者和消费者操作的完全并行运行。Doug Lea之所以没这样去做，也许是因为ArrayBlockingQueue的数据写入和获取操作已经足够轻巧，以至于引入独立的锁机制，除了给代码带来额外的复杂性外，其在性能上完全占不到任何便宜。 ArrayBlockingQueue和LinkedBlockingQueue间还有一个明显的不同之处在于，前者在插入或删除元素时不会产生或销毁任何额外的对象实例，而后者则会生成一个额外的Node对象。这在长时间内需要高效并发地处理大批量数据的系统中，其对于GC的影响还是存在一定的区别。而在创建ArrayBlockingQueue时，我们还可以控制对象的内部锁是否采用公平锁，默认采用非公平锁。
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/ArrayBlockingQueue.html
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {

        final Business business = new Business();

        new Thread(new Runnable(){

            public void run() {
                for(int i=0; i<50; i++){
                    business.sub(i);
                }
            }

        }).start();

        new Thread(new Runnable(){

            public void run() {
                for(int i=0; i<50; i++){
                    business.main(i);
                }
            }

        }).start();

    }


    static class Business{

        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);

        {
            try{
                queue2.put(1);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void sub(int i){


            try {
                queue1.put(1);
                System.out.println("线程" + Thread.currentThread().getName() +
                        "正在阻塞");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName() +
                    "开始运行");
            for(int j=1; j<=10; j++){
                System.out.println("sub thread sequence is " + j + " loop of " + i);
            }
            try {
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        public void main(int i){

            try {
                queue2.put(1);
                System.out.println("线程" + Thread.currentThread().getName() +
                        "正在阻塞");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName() +
                    "开始运行");
            for(int j=1; j<=10; j++){
                System.out.println("main thread sequence is " + j + " loop of " + i);
            }
            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
