package com.lb.book.thinkjava.p21;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by liub on 2017/3/5.
 */
class TestThreadLocal implements Runnable {
    private final int i;

    public TestThreadLocal(int id) {
        i = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    public String toString() {
        return i + "-" + ThreadLocalHolder.get() + "";
    }

}

public class ThreadLocalHolder {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        private Random r = new Random(47);

        protected synchronized Integer initialValue() {
            return r.nextInt(1);
        }
    };

    public static void increment() {
        value.set(value.get() + 1);
    }

    public static int get() {
        return value.get();
    }

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new TestThreadLocal(i));
        }
        TimeUnit.SECONDS.sleep(3);
        exec.shutdown();
    }
}