package com.lb.test.polymorphism.poly;

/**
 * Created by liub on 2016/12/13.
 */
public class A {

    void testA(){System.out.println("aaaaaaaaaaaaaaaaaa");}

    A(){
        System.out.println("-------------------");
        testA();
        System.out.println("++++++++++++++++");
    }
}
