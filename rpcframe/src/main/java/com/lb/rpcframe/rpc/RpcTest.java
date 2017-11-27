package com.lb.rpcframe.rpc;

import java.net.InetSocketAddress;

/**
 * 说明:
 * Created by LiuBing on 2017/11/23.
 */
public class RpcTest {

    public static void main(String[] args){
       RpcImporter<EchoService> importer = new RpcImporter<EchoService>();

       EchoService echo = importer.importer(EchoService.class, new InetSocketAddress("localhost", 8088));

       System.out.println(echo.echo("呵呵"));

       System.out.println(echo.hehe(1, 2));
    }

}
