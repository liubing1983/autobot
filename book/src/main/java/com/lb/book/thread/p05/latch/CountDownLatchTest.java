package com.lb.book.thread.p05.latch;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁 p79
 * Created by samsung on 2017/9/22.
 */
public class CountDownLatchTest {

    // 闭锁, 需要被调用两次
    final static CountDownLatch countDownLatch = new CountDownLatch(2);


    /**
     * 模拟资源初始化
     */
    private void init() {
        System.out.println("资源1开始初始化");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 通知闭锁初始化完成
        countDownLatch.countDown();
        System.out.println("资源1初始化完成!!!");
    }

    private void init2() {
        System.out.println("资源2开始初始化");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.out.println("资源2初始化完成!!!!!");
    }


    public static void main(String[] args) {

        for (int i = 0; i <= 10; i++) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        // 等待闭锁开启
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 执行");
                }
            };

            thread.start();
        }

        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        countDownLatchTest.init();
        countDownLatchTest.init2();
    }

}
