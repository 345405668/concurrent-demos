package com.lzx.learn.executor.executor;

import java.util.concurrent.Executor;

import static java.lang.Thread.sleep;

/**
 * Created by luozhixin on 14-9-1.
 * simple impl
 */
public class ExecutorImplClassDemo implements Executor{

    @Override
    public void execute(Runnable command) {
        //command.run();
        new Thread(command).start();
    }

    static class RunnableImpl implements Runnable {
        private Integer count = 0;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public void run() {
            count ++;
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runnableImpl print:" + count);
        }
    }

    public static void main(String[] args) {
        ExecutorImplClassDemo executorImplClassDemo = new ExecutorImplClassDemo();
        RunnableImpl runnable = new RunnableImpl();
        executorImplClassDemo.execute(runnable);
        System.out.println("main print:" + runnable.getCount());//executor会异步执行任务，此处会先打印结果
    }
}
