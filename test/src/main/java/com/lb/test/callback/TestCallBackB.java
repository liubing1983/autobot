package com.lb.test.callback;

/**
 * Created by liub on 2016/12/12.
 */
public class TestCallBackB implements  TestI {
    @Override
    public int ttt(int a, int b) {
        return a*b;
    }

    @Override
    public int hehe() {
return 10;
    }

    public int  abc(int a, int b, TestCallBackB ti) {
        return ti.ttt(a, b);
    }
}
