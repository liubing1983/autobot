package com.lb.netty.iodemo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @ClassName TimeClientHandler
 * @Description @TODO
 * @Author liubing
 * @Date 2019/11/21 11:34
 * @Version 1.0
 **/
public class TimeClientHandler implements Runnable {

    private Socket socket;

    public TimeClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        BufferedReader bufferedReader;
        PrintWriter printWriter;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            printWriter = new PrintWriter(this.socket.getOutputStream(), true);

            String s = null;

            while (true) {
                s = bufferedReader.readLine();
                if (s == null) break;

                if (s.equals("time"))
                    printWriter.println(System.currentTimeMillis());
                else
                    printWriter.println("time");
            }
        } catch (IOException e) {

        }
    }


}
