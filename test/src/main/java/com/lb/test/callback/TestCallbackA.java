package com.lb.test.callback;

/**
 * Created by liub on 2016/12/12.
 */
public class TestCallbackA  {

    public static void main(String[] args){
        TestCallBackB tb = new TestCallBackB();
        int i =  tb.abc(1, 2, tb);

        System.out.print(i);
    }

}
