package com.lzx.learn.i.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luozhixin on 14-9-22.
 * ConcurrentHashMap是一个线程安全的Hash Table，它的主要功能是提供了一组和HashTable功能相同但是线程安全的方法。ConcurrentHashMap可以做到读取数据不加锁，并且其内部的结构可以让其在进行写操作的时候能够将锁的粒度保持地尽量地小，不用对整个ConcurrentHashMap加锁。
 * 允许多个读操作并发进行，读操作并不需要加锁
 * ConcurrentHashMap为了提高本身的并发能力，在内部采用了一个叫做Segment的结构，一个Segment其实就是一个类Hash Table的结构，Segment内部维护了一个链表数组
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/ConcurrentHashMap.html
 * 线程安全的几个集合实现方法性能对比
 */
public class ConcurrentHashMapDemo {
    static final int threads = 1000;
    static final int NUMBER = 1000;
    public static void main(String[] args) throws Exception {
        Map<String, Integer> hashmapSync = Collections
                .synchronizedMap(new HashMap<String, Integer>());
        Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<String, Integer>();
        Map<String, Integer> hashtable = new Hashtable<String, Integer>();
        long totalA = 0;
        long totalB = 0;
        long totalC = 0;
        for (int i = 0; i <= 10; i++) {
            totalA += testPut(hashmapSync);
            totalB += testPut(concurrentHashMap);
            totalC += testPut(hashtable);
        }
        System.out.println("Put time HashMapSync=" + totalA + "ms.");
        System.out.println("Put time ConcurrentHashMap=" + totalB + "ms.");
        System.out.println("Put time Hashtable=" + totalC + "ms.");
        totalA = 0;
        totalB = 0;
        totalC = 0;
        for (int i = 0; i <= 10; i++) {
            totalA += testGet(hashmapSync);
            totalB += testGet(concurrentHashMap);
            totalC += testGet(hashtable);
        }
        System.out.println("Get time HashMapSync=" + totalA + "ms.");
        System.out.println("Get time ConcurrentHashMap=" + totalB + "ms.");
        System.out.println("Get time Hashtable=" + totalC + "ms.");
    }
    public static long testPut(Map<String, Integer> map) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            new MapPutThread(map).start();
        }
        while (MapPutThread.counter > 0) {
            Thread.sleep(1);
        }
        return System.currentTimeMillis() - start;
    }
    public static long testGet(Map<String, Integer> map) throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            new MapPutThread(map).start();
        }
        while (MapPutThread.counter > 0) {
            Thread.sleep(1);
        }
        return System.currentTimeMillis() - start;
    }
}
class MapPutThread extends Thread {
    static int counter = 0;
    static Object lock = new Object();
    private Map<String, Integer> map;
    private String key = this.getId() + "";
    MapPutThread(Map<String, Integer> map) {
        synchronized (lock) {
            counter++;
        }
        this.map = map;
    }
    public void run() {
        for (int i = 1; i <= ConcurrentHashMapDemo.NUMBER; i++) {
            map.put(key, i);
        }
        synchronized (lock) {
            counter--;
        }
    }
}
class MapGetThread extends Thread {
    static int counter = 0;
    static Object lock = new Object();
    private Map<String, Integer> map;
    private String key = this.getId() + "";
    MapGetThread(Map<String, Integer> map) {
        synchronized (lock) {
            counter++;
        }
        this.map = map;
    }
    public void run() {
        for (int i = 1; i <= ConcurrentHashMapDemo.NUMBER; i++) {
            map.get(key);
        }
        synchronized (lock) {
            counter--;
        }
    }

}
