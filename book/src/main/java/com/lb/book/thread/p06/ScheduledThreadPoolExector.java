package com.lb.book.thread.p06;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 延迟执行或周期执行的线程池 p101
 * Created by samsung on 2017/8/30.
 */
public class ScheduledThreadPoolExector {

    public static void main(String[] args){

        // 创建一个延迟执行的线程池
        ScheduledExecutorService execs = Executors.newScheduledThreadPool(5);

        for(int i = 0; i< 20; i++){
            execs.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                    } finally {
                        // execs.shutdown();
                    }
                }
            }, 10, TimeUnit.SECONDS);
        }

        // 我们可以使用该方法延迟执行任务，设置任务的执行周期。时间周期从线程池中首先开始执行的线程算起，所以假设period为1s，线程执行了5s，那么下一个线程在第一个线程运行完后会很快被执行。
        // execs.scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit)

        // 该方法可被用于延迟周期性执行任务，delaytime是线程停止执行到下一次开始执行之间的延迟时间
        // execs.scheduleWithFixedDelay(Runnable command,long initialDelay,long delay,TimeUnit unit)

        execs.shutdown();

    }

}
