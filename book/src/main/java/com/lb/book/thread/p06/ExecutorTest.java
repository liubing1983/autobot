package com.lb.book.thread.p06;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Executor 框架   P96
 * Created by liub on 2017/2/24.
 */
public class ExecutorTest {

    // 创建一个固定线程池
    private static final Executor exec = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        while (true) {
            // 创建线程
            Runnable r = new Runnable() {
                @Override
                public void run() {

                }
            };
            // 执行线程
            exec.execute(r);
        }
    }


}
