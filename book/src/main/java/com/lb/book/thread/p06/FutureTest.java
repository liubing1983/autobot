package com.lb.book.thread.p06;


import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池的回调函数 p105
 * Created by samsung on 2017/8/30.
 */
public class FutureTest {

    private static final ExecutorService execs = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Random r = new Random();
                Boolean b = r.nextBoolean();
                System.out.println(Thread.currentThread().getName() + "---" + b);
                return b;
            }
        };
        int i = 0;

        while(true) {
            Future<Boolean> future = execs.submit(callable);
            try {
                Boolean s = future.get();
                if (s) {
                    i++;
                }
                System.out.println(s+"--"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally {
                if (i >= 10) execs.shutdown();
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
