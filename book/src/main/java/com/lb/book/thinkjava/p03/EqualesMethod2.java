package com.lb.book.thinkjava.p03;

/**
 * Created by liub on 2017/2/21.
 */
public class EqualesMethod2 {

    public static void main(String[] args){
        Value v1 = new Value();
        Value v2 = new Value();
        v1.i = v2.i = 100;

        System.out.println(v1.equals(v2));

    }

}

class Value{
    int i;
}
