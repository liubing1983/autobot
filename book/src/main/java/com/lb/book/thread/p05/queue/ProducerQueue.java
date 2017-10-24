package com.lb.book.thread.p05.queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static sun.misc.Version.println;

/**
 * Created by samsung on 2017/9/14.
 */
public class ProducerQueue {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(5);

        QueueManager qm = new QueueManager();

        ArrayBlockingQueue<String> queue = qm.getQueue();

        // 随机数
        Random random = new Random();

        Runnable r = new Runnable() {
            @Override
            public void run() {

                // 生成随机数
                int i = random.nextInt(5);

               queue.add("Hello : " + i);
                System.out.println(Thread.currentThread().getName()+"-"+i+"--"+queue.size());
                try {
                    // 每个线程延时i秒
                    Thread.sleep(i * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            service.submit(r);
        }
        //service.shutdown();

    }
}
