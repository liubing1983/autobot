package com.lb.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 根据数据最后写入时间更新cache中的数据.
 * cache中的数据存在更新不及时的情况
 */
public class EsIndexCache {

       Cache<String,String> cache = null;

       // 初始化cache
     public  void init(){
         cache = CacheBuilder.newBuilder()
                 .maximumSize(100)  // 缓存数目

                 /**
                  * 两个如果同时配置, 按时间短的触发
                  */
                 // .expireAfterAccess(3, TimeUnit.SECONDS) //没有读取操作 3秒后过期
                 .expireAfterWrite(3, TimeUnit.SECONDS)  // 没有写入/覆盖操作 3秒后过期.
                 .build();
     }

    /**
     * 模拟取数业务逻辑
     * @return
     */
     public  String getRandom() {
         Random random1 = new Random();
         return random1.nextInt(100)+"";
    }


    /**
     * 从缓存中得到数据
     * @param key
     * @return
     */
    public   String getCallableCache(String key) {
        try {
            //Callable只有在缓存值不存在时，才会调用
            return cache.get(key, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(key+" from es");
                    // 模拟从数据库取数逻辑
                    return getRandom();
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        // 随系统初始化
        EsIndexCache e = new EsIndexCache();
        e.init();

        for(int i = 0; i< 10; i++){
            // 程序访问时调用
            System.out.println(e.getCallableCache("abc"));
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
