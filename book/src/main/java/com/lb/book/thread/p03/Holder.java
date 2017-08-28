package com.lb.book.thread.p03;

/**
 * Created by liub on 2017/2/21.
 */
public class Holder {

    private int n;

    public Holder(int m){this.n = n;}

    public void assertSanity(){
        if(n != n){
            throw new AssertionError("error");
        }
    }

    private static  class Tt extends  Thread{
        public void run(){
            Holder h = new Holder(5);
            h.assertSanity();
        }
    }

    public static void main(String[] args){
        Holder h = new Holder(10);
        h.assertSanity();

       new Tt().start();
    }
}
