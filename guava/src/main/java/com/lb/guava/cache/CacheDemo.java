package com.lb.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheDemo {

        public static void main(String[] args) {
            CacheDemo.loadingCacheDemo();
            CacheDemo.callbackDemo();
        }

        public static void loadingCacheDemo(){
            LoadingCache<String,String> cache= CacheBuilder.newBuilder()
                    .maximumSize(100) //最大缓存数目
                    .expireAfterAccess(3, TimeUnit.SECONDS) //缓存1秒后过期
                    .build(new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) throws Exception {
                            return key;
                        }
                    });
            cache.put("j","java");
            cache.put("c","cpp");
            cache.put("s","scala");
            cache.put("g","go");
            try {
                System.out.println(cache.get("j"));
                TimeUnit.SECONDS.sleep(2);
                System.out.println(cache.get("s")); //输出s
                TimeUnit.SECONDS.sleep(20000000);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }


        public  static void callbackDemo(){

            Cache<String,String> cache= CacheBuilder.newBuilder()
                    .maximumSize(100)
                    .expireAfterAccess(4, TimeUnit.SECONDS)
                    .build();
            try {
                String result=cache.get("j", () -> "hello java");
                System.out.println(result);
                String result2=cache.get("java", () -> "hello java222");
                System.out.println(result2);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
}
