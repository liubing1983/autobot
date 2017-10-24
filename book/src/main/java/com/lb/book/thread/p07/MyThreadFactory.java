package com.lb.book.thread.p07;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by samsung on 2017/9/5.
 */
public class MyThreadFactory implements ThreadFactory {


    // 用来储存线程对象的数量
    private int counter;
    //每个创建线程的名字
    private String name;
    // 字符串list, 储存创建的线程对象的统计数据
    private List<String> stats;

    /**
     * 实现类的构造函数并初始化
     *
     * @param name
     */
    public MyThreadFactory(String name) {
        counter = 0;
        this.name = name;
        stats = new ArrayList<String>();
    }

    /**
     * 实现 newThread() 方法.
     * 此方法 会接收Runnable接口并返回一个 Thread 对象给这个 Runnable 接口。
     * 在这里, 我们生成线程对象的名字，然后创建新的线程对象，最后保存统计数据。
     *
     * @param r
     * @return
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, name + "-Thread_" + counter);
        counter++;
        stats.add(String.format("created thread %d with name %s on %s\n", t.getId(), t.getName(), new Date()));

        return t;
    }

    /**
     * 实现 getStatistics()方法 ，返回 String 对象， 全部的创建的 Thread 对象的统计数据。
     *
     * @return
     */
    public String getStats() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()) {
            buffer.append(it.next()).append("\n");
        }
        return buffer.toString();
    }

//    创建一个类名为 Task 一定实现 Runnable接口. 对于这个例子，这些任务什么都不做只是休眠一秒。
    public static class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        // 创建 MyThreadFactory 对象和 Task 对象。

        // 赋值并初始化
        MyThreadFactory factory = new MyThreadFactory("MyThreadFactory");
        // 创建一个休眠一秒的线程
        Task task = new Task();

        //  使用 MyThreadFactory 对象创建 10 Thread 对象并开始它们.
        Thread thread;
        System.out.printf("Starting the Threads\n");
        for (int i = 0; i < 10; i++) {
            thread = factory.newThread(task);
            thread.start();
        }

        // 把线程工厂的数据写入控制台。
        System.out.printf("Factory stats:\n");
        System.out.printf("%s\n", factory.getStats());
    }

}
