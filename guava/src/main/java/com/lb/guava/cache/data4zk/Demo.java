package com.lb.guava.cache.data4zk;

import com.google.common.cache.Cache;
import com.lb.guava.cache.data4zk.cache.GuavaCacheUtils;
import com.lb.guava.cache.data4zk.zk.ZkUtils;
import org.apache.curator.framework.CuratorFramework;

public class Demo {


    public static void main(String[] args) throws Exception{

        // 初始化zk
        ZkUtils zku = new ZkUtils();
        CuratorFramework client = zku.getCuratorFrameworkClient();
       // zku.initZk();
        client.start();

        // 初始化缓存
        GuavaCacheUtils<String, String > guavecache = new GuavaCacheUtils();
        Cache<String, String > cache = guavecache.getGuavaCache();

        String s = guavecache.getCallableCache(cache, "key1");

        System.out.println(s);

    }

}
