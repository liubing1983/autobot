package com.lb.netty.iodemo.bio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName TimeServer 同步阻塞式I/O
 * @Description @TODO
 * @Author liu bing
 * @Date 2019/11/21 10:47
 * @Version 1.0
 **/
public class TimeServer {


    public static void main(String[] args) {

        /**
         * 创建一个最简单的server, 绑定端口
         */
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket socket;

            while (true) {
                // 在循环内不停的监控
                socket = serverSocket.accept();
                new Thread(new TimeClientHandler(socket)).start();
            }
        } catch (IOException e) {
            // 关闭 serverSocket
        }
    }
}
