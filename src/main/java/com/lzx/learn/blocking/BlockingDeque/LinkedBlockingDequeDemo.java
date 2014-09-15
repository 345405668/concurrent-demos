package com.lzx.learn.blocking.BlockingDeque;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by luozhixin on 14-9-14.
 * 一个基于已链接节点的、任选范围的阻塞双端队列。
 * 可选的容量范围构造方法参数是一种防止过度膨胀的方式。如果未指定容量，那么容量将等于 Integer.MAX_VALUE。只要插入元素不会使双端队列超出容量，每次插入后都将动态地创建链接节点。
 * 大多数操作都以固定时间运行（不计阻塞消耗的时间）。
 * LinkedBlockingDeque是“线程安全”的队列，而LinkedList是非线程安全的。
 * 下面是“多个线程同时操作并且遍历queue”的示例
 *   (01) 当queue是LinkedBlockingDeque对象时，程序能正常运行。
 *   (02) 当queue是LinkedList对象时，程序会产生ConcurrentModificationException异常。
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/LinkedBlockingDeque.html
 */
public class LinkedBlockingDequeDemo {
    // TODO: queue是LinkedList对象时，程序会出错。
    //private static Queue<String> queue = new LinkedList<String>();
    private static Queue<String> queue = new LinkedBlockingDeque<String>();
    public static void main(String[] args) throws InterruptedException {

        // 同时启动两个线程对queue进行操作！
        new MyThread("ta").start();
        //Thread.sleep(1000);
        new MyThread("tb").start();
    }

    private static void printAll() {
        String value;
        Iterator iter = queue.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            int i = 0;
            while (i++ < 6) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName()+i;
                queue.add(val);
                // 通过“Iterator”遍历queue。
                printAll();
            }
        }
    }
}
