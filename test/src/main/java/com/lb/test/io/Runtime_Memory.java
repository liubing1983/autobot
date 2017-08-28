package com.lb.test.io;

/**
 * Created by liub on 2016/12/9.
 */
public class Runtime_Memory {
    public static void main(String args[]) {

        try {
            long start = System.currentTimeMillis();
            System.out.println("程序开始执行的时间为：" + start);
            Thread.sleep(2000);
            long end = System.currentTimeMillis();
            System.out.println("程序运行结束的时间为: " + end);
            System.out.println("程序执行时间为：" + (end - start) + "毫秒");
        } catch (InterruptedException el) {
            el.printStackTrace();
        }

        try {
            Runtime run = Runtime.getRuntime();// 获取与当前运行类相关联的runtime实例
            System.out.println("内存可用空间：" + run.totalMemory());
            System.out.println("未创建数组时剩余内存空间：" + run.freeMemory());
            int base[] = new int[10240]; // 申请空间
            System.out.println("创建数组后剩余内存空间：" + run.freeMemory());
            run.gc();// 启动垃圾收集线程
            Thread.sleep(1000);
            System.out.println("启动垃圾收集之后剩余空间：" + run.freeMemory());
            System.out.println("======为数组分配空间=====");
            for (int i = 0; i < 10240; ++i)
                base[i] = i + 1;
            Thread.sleep(2000);
            System.out.println("分配空间之后剩余空间：" + run.freeMemory());
            run.gc();// 启动垃圾收集线程
            Thread.sleep(2000);
            System.out.println("启动垃圾收集之后剩余空间：" + run.freeMemory());
        } catch (InterruptedException el) {
            el.printStackTrace();
        }
    }
}

/**
 运用Runtime类来获取相应的内存信息

 1.利用totalMemory()方法来获取虚拟机的总内存

 2.利用freeMemory()方法来获取虚拟机中空闲的内存大小

 3.利用gc()来启动垃圾收集线程
 */