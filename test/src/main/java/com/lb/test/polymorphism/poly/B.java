package com.lb.test.polymorphism.poly;

/**
 * Created by liub on 2016/12/13.
 */
public class B extends A {

    private int r = 1;

    B(int i){

        r = i;
        System.out.println("B.B(), r= "+ r);
    }

    void testA(){System.out.println("B.testa(), r = "+ r);}

}
