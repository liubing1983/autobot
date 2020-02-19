package com.lb.netty.iodemo.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName TimeClient
 * @Description @TODO
 * @Author liubing
 * @Date 2019/11/21 14:25
 * @Version 1.0
 **/
public class TimeClient {

    public static void main(String[] args) {
        Socket socket;
        BufferedReader bufferedReader;
        PrintWriter printWriter;

        try {
            socket = new Socket("127.0.0.1", 8080);

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter = new PrintWriter(socket.getOutputStream(), true);

            // 通过socket发送请求
            printWriter.println("lb");
            // 打印结果
            System.out.println(bufferedReader.readLine());

            printWriter.println("time");
            System.out.println(bufferedReader.readLine());

        } catch (Exception e) {
            // 按顺序关闭 printWriter, bufferedReader, socket
        }
    }
}
