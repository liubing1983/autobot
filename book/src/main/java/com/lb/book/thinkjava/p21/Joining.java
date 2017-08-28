package com.lb.book.thinkjava.p21;

/**
 * Created by liub on 2017/3/3.
 */
public class Joining {

    public static void main(String[] args){
        Thread t1 = new Thread(new A());
        Thread t2 = new Thread(new B());
        t2.start();
        try {
            // join存在输出  B1  B2 a
            // 不存在输出  a  B1  B2   或  B1  a  B2
            // join存在可以确保t2执行完成后再执行t1
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.start();
    }
}

class A implements Runnable{
    @Override
    public void run(){
        System.out.println("a");
    }
}

class B implements Runnable{
    public void  run(){
        System.out.println("B1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("B2");
    }
}
