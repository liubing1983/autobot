package com.lb.book.thinkjava.p5;

/**
 * Created by liub on 2016/12/7.
 */
public class StaticTest {

    static {
        System.out.println("static");
    }

    StaticTest(){
        System.out.println("StaticTest");
    }

    public static void main(String[] args){
        System.out.println("main");
    }
}
