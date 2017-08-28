package com.lb.thread.test.p01;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liub on 2017/2/28.
 */
public class PrintLogExecutor {
    static BlockingQueue<String> q = new ArrayBlockingQueue<String>(16);
    static ExecutorService exec = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        System.out.println("begin:" + System.currentTimeMillis() / 1000);
        /**
         * 模拟处理16行日志, 下面的代码产生了16个日志对象
         */
        // 这行代码不能改动
        for (int i = 0; i < 16; i++) {
            // 这行代码不能改动, 生成的日志信息
            final String log = "" + (i + 1);
            {
                q.add(log);
                // 打印日志信息
                //PrintLogThread.parseLog(log);


            }
        }
        PrintLogExecutor.testLog();
        // 关闭线程池
        while (true) {
            if (q.isEmpty()) {
                System.out.print("q.isEmpty()"+exec.isShutdown());
                exec.shutdown();
                System.out.print("q.isEmpty()"+exec.isShutdown());
                break;
            }else{
                try {
                    System.out.println("isShutdown:"+exec.isShutdown());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void testLog() {
        while (!q.isEmpty()) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        parseLog(q.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(new Thread(r));
        }
        System.out.println(q.isEmpty());
    }

    /**
     * 方法内部不能改动
     *
     * @param log
     */
    private static void parseLog(String log) {
        System.out.println(log + ":" + (System.currentTimeMillis() / 1000));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
