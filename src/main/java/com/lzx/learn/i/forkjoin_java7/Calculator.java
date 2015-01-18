package com.lzx.learn.i.forkjoin_java7;

import java.util.concurrent.RecursiveTask;

/**
 * Created by luozhixin on 15/1/14. Fo
 * RecursiveTask http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/RecursiveTask.html
 * RecursiveAction  http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/RecursiveAction.html
 * RecursiveAction供不需要返回值的任务继续。
 * RecursiveTask通过泛型参数设置计算的返回值类型。
 * 都继承于ForkJoinTask<V>类，而ForkJoinTask<V>实现了Future<V>接口
 */
public class Calculator extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 100;
    private int start;
    private int end;

    public Calculator(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if((start - end) < THRESHOLD){
            for(int i = start; i< end;i++){
                sum += i;
            }
        }else{
            int middle = (start + end) /2;
            Calculator left = new Calculator(start, middle);
            Calculator right = new Calculator(middle + 1, end);
            left.fork();
            right.fork();

            sum = left.join() + right.join();
        }
        return sum;
    }

}
