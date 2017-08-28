package com.lb.book.thinkjava.p21.resourcecompete;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liub on 2017/3/5.
 */
public class EvenChecker implements Runnable{

    private IntGennrator gennrator;

    private  final int id;

    public  EvenChecker(IntGennrator g, int ident){
        gennrator  = g;
        id = ident;
    }

    @Override
    public void run() {
        // 判断数字是否是偶数
        while(!gennrator.isCanceled()){
            int val  = gennrator.next();
            if(val % 2 != 0){
                // 不是偶数输出错误
                System.out.println(val + "not even!!!");
                // 停止循环
                gennrator.cancel();
            }else{
                //System.out.println(val );
            }
        }
    }

    public static void test(IntGennrator g, int count){
        System.out.println("Pree");
        // 启动线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i< count; i++){
            exec.execute(new EvenChecker(g, i));
        }
        exec.shutdown();
    }

    public static void test(IntGennrator g){
        test(g, 10);
    }
}
