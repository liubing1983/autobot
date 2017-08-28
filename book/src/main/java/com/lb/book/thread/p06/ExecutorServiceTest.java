package com.lb.book.thread.p06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService接口, 扩展了线程池的生命周期方法  P100
 * Created by liub on 2017/2/27.
 */
public class ExecutorServiceTest {

    private static final ExecutorService execs = Executors.newFixedThreadPool(100);

    public static void main(String[] args){
        for(int i = 0; i<= 200; i++){
            execs.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        test();
                    } finally {
                       // execs.shutdown();
                    }
                }
            });
        }
        execs.shutdown();
    }

    private  static void test(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}