package com.lb.rpcframe.rpc;

/**
 * 说明:
 * Created by LiuBing on 2017/11/23.
 */
public class RpcServer {

    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcExporter.exporter("localhost", 8088);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}