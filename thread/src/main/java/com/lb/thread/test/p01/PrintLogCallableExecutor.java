package com.lb.thread.test.p01;

import java.util.concurrent.*;

/**
 * Created by liub on 2017/2/28.
 */
public class PrintLogCallableExecutor {

    static BlockingQueue<String> q = new ArrayBlockingQueue<String>(16);
    static ExecutorService exec = Executors.newFixedThreadPool(4);

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
        PrintLogCallableExecutor.testLog();
    }

    private static void testLog() {
        while (!q.isEmpty()) {
            Callable<String> callable = new Callable() {
                @Override
                public String call() throws Exception {
                    while (!q.isEmpty()) {
                        PrintLogCallableExecutor.parseLog(q.take());
                    }
                    return q.take();
                }
            };
            Future<String> f = exec.submit(callable);

            try {
                System.out.println(f.get(1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                System.out.println("123123123");
            } finally {
                    f.cancel(true);
            }


        }
        System.out.println(q.isEmpty());
        // 关闭线程池
        if (q.isEmpty()) exec.shutdown();
    }

    /**
     * 方法内部不能改动
     *
     * @param log
     */
    private static void parseLog(String log) {
        System.out.println(log + ":" + (System.currentTimeMillis() / 1000));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
