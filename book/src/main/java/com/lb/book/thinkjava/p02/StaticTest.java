package com.lb.book.thinkjava.p02;

/**
 * Created by liub on 2017/2/21.
 */
public class StaticTest {

    static int i =  10;

    public static void main(String[] args){
        StaticTest s1 = new StaticTest();
        StaticTest s2 = new StaticTest();

        System.out.println(s1.i);
        System.out.println(s2.i);

        i = 12;
        System.out.println(s1.i);
        System.out.println(s2.i);

        s1.i = 13;
        System.out.println(s1.i);
        System.out.println(s2.i);
    }
}
