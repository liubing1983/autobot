package com.lb.book.thread.p07;

/**
 * 测试线程终止时, sleep的操作  P114
 *
 *  sleep和 wait都会检测interrupt的状态
 * Created by samsung on 2017/9/25.
 */
public class SleepInterrupt extends Thread {

    public void run() {
        System.out.println(Thread.currentThread().isInterrupted()+"---");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println("sleep end");
    }

    public static void main(String[] args) {
        SleepInterrupt si = new SleepInterrupt();

        si.start();

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// 如果设置中断, sleep会抛出java.lang.InterruptedException: sleep interrupted  异常, 并提前终止sleep
        si.interrupt();
    }
}
