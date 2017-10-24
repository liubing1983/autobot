package com.lb.book.thread.p03;

/**
 * 使用ThreadLocal来维持线程的封闭性  P37
 * Created by samsung on 2017/8/29.
 */
public class TestThreadLocal {

    public static class MyRunnable implements Runnable {

        // 在多个线程中数据不共享, 将变量保存在各自的线程栈中
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            /**
             * 重写ThreadLocal的initialValue方法, 并设置初始值.
             * @return
             */
            @Override protected Integer initialValue() {
                return 0;
            }
        };

        /**
         * 使用普通的共享变量, 后执行的线程会覆盖前一个线程
         */
        //private int threadLocal = 0;

        /**
         * 使用volatile
         */
        // private volatile int threadLocal = 0;

        @Override
        public void run() {
            // 给共享变量赋值
            threadLocal.set((int) (Math.random() * 100D));
            //threadLocal = (int)(Math.random() * 100);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(Thread.currentThread().getName()+"--"+threadLocal.get());
            //System.out.println(Thread.currentThread().getName()+"==="+threadLocal);
        }
    }

    public static void main(String[] args) {
        MyRunnable sharedRunnableInstance = new MyRunnable();
        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        //thread1.join(); //wait for thread 1 to terminate
        //thread2.join(); //wait for thread 2 to terminate
    }
}