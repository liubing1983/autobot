package com.lb.zookeeper.curator.crud;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by liub on 2016/12/2.
 */
public class Demo {

    static {
        System.out.println("init-----------------------------------------------------");
    }

    private  void init(){
        // 重试策略  （sleep时间， 重试次数）
        RetryPolicy rp = new ExponentialBackoffRetry(1000, 3);

        // 创建客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient("", 5000, 3000, rp);

    }

    public static void main(String[] args){

    }
}
