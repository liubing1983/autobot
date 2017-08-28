package com.lb.book.thread.p03;

/**
 * 数据的重排序 p27
 * Created by liub on 2017/2/21.
 */
public class Novisibility {

    // 声明volatile 后, 编译器认为该参数是共享的.
    private  static volatile boolean ready;
    private  static volatile int number = 9;

    private  static class ReaderTherad extends Thread{
        public void run(){
            System.out.println("---------------------");
            while(!ready){
                Thread.yield();
                System.out.println("number:  "+ number);
            }
        }
    }

    public static void main(String[] args){
        new ReaderTherad().start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 如果不加volatile ReaderTherad程序读不到number的值
        // 虽然number在ready前被修改
        // 这种现象叫重排序
        number = 42;
        ready = true;
    }
}
