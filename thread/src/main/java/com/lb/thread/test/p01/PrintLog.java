package com.lb.thread.test.p01;

/**
 * 现有的程序代码模拟产生了16个日志对象，并且需要运行16秒才能打印完这些日志，
 * 请在程序中增加4个线程去调用parseLog()方法来分头打印这16个日志对象，
 * 程序只需要运行4秒即可打印完这些日志对象。原始代码如下
 * Created by liub on 2017/2/28.
 */
public class PrintLog {

    public static void main(String[] args){
        System.out.println("begin:"+ System.currentTimeMillis() / 1000);
        /**
         * 模拟处理16行日志, 下面的代码产生了16个日志对象
         */
        // 这行代码不能改动
        for(int i = 0; i < 16 ; i++){
            // 这行代码不能改动, 生成的日志信息
            final String log = "" + (i + 1);
            {
                // 打印日志信息
                PrintLog.parseLog(log);
            }
        }
    }

    /**
     * 方法内部不能改动
     * @param log
     */
    private static void parseLog(String log){
        System.out.println(log + ":" + (System.currentTimeMillis() / 1000));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
