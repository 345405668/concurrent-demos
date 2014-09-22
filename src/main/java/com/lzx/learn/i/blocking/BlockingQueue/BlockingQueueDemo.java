package com.lzx.learn.i.blocking.BlockingQueue;

/**
 * Created by luozhixin on 14-9-10.
 * 阻塞队列BlockingQueue
 * 支持两个附加操作的 Queue，这两个操作是：获取元素时等待队列变为非空，以及存储元素时等待空间变得可用。
 * BlockingQueue 方法以四种形式出现，对于不能立即满足但可能在将来某一时刻可以满足的操作，这四种形式的处理方式不同：第一种是抛出一个异常，第二种是返回一个特殊值（null 或 false，具体取决于操作），第三种是在操作可以成功前，无限期地阻塞当前线程，第四种是在放弃前只在给定的最大时间限制内阻塞。
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/BlockingQueue.html
 */
public class BlockingQueueDemo {

}
