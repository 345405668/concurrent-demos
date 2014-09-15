package com.lzx.learn.blocking.BlockingQueue;

/**
 * Created by luozhixin on 14-9-11.
 * DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。DelayQueue是一个没有大小限制的队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞。
 * 使用场景：DelayQueue使用场景较少，但都相当巧妙，常见的例子比如使用一个DelayQueue来管理一个超时未响应的连接队列。
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/util/concurrent/DelayQueue.html
 */

import com.lzx.learn.blocking.domain.Student;
import com.lzx.learn.blocking.domain.Teacher;

import java.util.concurrent.Executors;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;







public class DelayQueueDemo {
    /**
     * 模拟考试，时间为120分钟，学生可以再30分钟后交卷，
     * 当学生都交完了 或 时间到者考试结束
     * @author 小e
     *
     * 2010-4-30 下午11:14:25
     */

    static final int STUDENT_SIZE = 45;
    public static void main(String[] args) {
        Random r = new Random();
        DelayQueue<Student> students = new DelayQueue<Student>();
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < STUDENT_SIZE; i++){
            students.put(new Student("学生" + ( i + 1), 3000 + r.nextInt(9000)));
        }
        students.put(new Student.EndExam(12000,exec));//1200为考试结束时间
        exec.execute(new Teacher(students, exec));

    }
}
