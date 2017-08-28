package com.lb.zookeeper.curator.lock;

/**
 * Created by liub on 2016/12/8.
 */
import com.lb.zookeeper.curator.ZkConnection;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Lock {
    static String lock_path = "/lb";

    public static void main(String[] args){
        // 创建连接
        ZkConnection zkc = new ZkConnection("lb", "10.95.3.136:2181,10.95.3.138:2181");
        CuratorFramework client = zkc.getZKConnection();
        client.start();

        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        final CountDownLatch down = new CountDownLatch(1);

        for(int i = 0; i<= 10; i++){
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                down.await();
                                // 获取锁
                                lock.acquire();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            SimpleDateFormat  sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                            String s = sdf.format(new Date());
                            System.out.println("no:"+s);
                            try {
                                // 释放锁
                                lock.release();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        }
        down.countDown();
    }
}
