package com.lb.book.thinkjava.p21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程API基本用法
 * Created by liub on 2017/3/3.
 */
public class LiftOff implements Runnable {

    // 默认值
    protected int countDown = 10;

    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return id + ":" + (countDown > 0 ? countDown : 999)+"   ";
    }

    @Override
    public void run() {
        int i  = 0;
        while (countDown-- > 0) {
            System.out.print(status());
            if(i > 5){
                System.out.println();
                i = 0;
            }
            //Thread.yield();
        }
    }

    // 启动一个线程
    private static void oneThread(){
        Thread t = new Thread(new LiftOff());
        t.start();
    }
// 启动多个线程
    private static void moreThread(){
        for(int i  = 0; i < 5; i++){
            Thread t = new Thread(new LiftOff(6));
            t.start();
        }
    }

    private static void exectorCache(){
        ExecutorService e = Executors.newCachedThreadPool();
        for(int i  = 0; i < 5; i++){
            Thread t = new Thread(new LiftOff());
            e.execute(new LiftOff());
        }
        e.shutdown();
    }

    public static void main(String[] args){
        //oneThread();
       // moreThread();
        exectorCache();
    }
}