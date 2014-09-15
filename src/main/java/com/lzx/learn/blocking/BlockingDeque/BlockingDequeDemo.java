package com.lzx.learn.blocking.BlockingDeque;

/**
 * Created by luozhixin on 14-9-10.
 * BlockingDeque  双向队列
 * 支持两个附加操作的 Queue，这两个操作是：获取元素时等待双端队列变为非空；存储元素时等待双端队列中的空间变得可用。
 * BlockingDeque 方法有四种形式，使用不同的方式处理无法立即满足但在将来某一时刻可能满足的操作：第一种方式抛出异常；第二种返回一个特殊值（null 或 false，具体取决于操作）；第三种无限期阻塞当前线程，直至操作成功；第四种只阻塞给定的最大时间，然后放弃
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/BlockingDeque.html
 */
public class BlockingDequeDemo {
}
