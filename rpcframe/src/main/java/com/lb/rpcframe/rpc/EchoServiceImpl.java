package com.lb.rpcframe.rpc;

/**
 * 说明: 业务具体实现
 * Created by LiuBing on 2017/11/23.
 */
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String s) {
        return "hello, " + s;
    }

    @Override
    public int hehe(int a, int b) {
        return a + b;
    }

}
