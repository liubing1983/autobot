package com.lb.book.thinkjava.p21;

/**
 * 两个线程去调用同步代码块或同步方法, 没有产生阻塞
 * Created by liub on 2017/3/5.
 */
class DualSynch {

    private Object syncObject = new Object();

    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void g(){
        synchronized (syncObject){
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

public class SyncObject{
    public  static  void  main(String[] args){
        final DualSynch ds = new DualSynch();
        new Thread(){
           @Override
            public void run(){
               ds.f();
            }
        }.start();
        ds.g();
    }
}