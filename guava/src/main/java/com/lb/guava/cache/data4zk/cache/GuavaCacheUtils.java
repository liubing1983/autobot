package com.lb.guava.cache.data4zk.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.lb.guava.cache.data4zk.zk.ZkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaCacheUtils<K, V> {

    Logger log = LoggerFactory.getLogger(GuavaCacheUtils.class);

    private Cache<K, V> cache = null;

    // 缓存时间
    private int expireTime;
    // 缓存时间单位
    private TimeUnit expireTimeUnit;
    // 最大缓存数目
    private int maximumSize;
    // 缓存过期策略
    private String expireAfterType;


    public GuavaCacheUtils() {
        this.expireTime = 10;
        this.expireTimeUnit = TimeUnit.SECONDS;
        this.maximumSize = 100;
        this.expireAfterType = "Write";
    }

    public GuavaCacheUtils(int expireTime, TimeUnit expireTimeUnit, String expireAfterType, int maximumSize) {
        this.expireTime = expireTime;
        this.expireTimeUnit = expireTimeUnit;
        this.maximumSize = maximumSize;
        this.expireAfterType = expireAfterType;
    }


    /**
     * 初始化cache, 单例
     * @return
     * @throws Exception
     */
    public Cache<K, V> getGuavaCache() throws Exception{
        if (cache == null) {
            if (expireAfterType == "Write") {
                cache = CacheBuilder.newBuilder().maximumSize(maximumSize)  // 缓存数目
                        .expireAfterWrite(expireTime, expireTimeUnit)  // 没有写入/覆盖操作 3秒后过期
                        .build();
            } else if (expireAfterType == "Access") {
                cache = CacheBuilder.newBuilder().maximumSize(maximumSize)  // 缓存数目
                        .expireAfterAccess(expireTime, expireTimeUnit) //没有读取操作 3秒后过期
                        .build();
            }else{
                throw new Exception("expireAfterType取值为: Write/Access");
            }
        }
        return cache;
    }

    /**
     * 从缓存中得到数据
     * @param key
     * @return
     */
    public <V> V getCallableCache(Cache<K, V> cache,K key) {
        try {
            //Callable只有在缓存值不存在时，才会调用
            return cache.get(key, new Callable<V>() {
                @Override
                public V call() throws Exception {
                    // 模拟从数据库取数逻辑
                    log.info("被动更新缓存, key:"+key+", value:"+key);
                    ZkUtils zku = new ZkUtils();
                   return  zku.finkZk(zku.getCuratorFrameworkClient(), String.valueOf(key));
                   // return  (V)"123";
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新缓存
     * @param cache
     * @param key
     * @param value
     */
    public void putCallableCache(Cache<K, V> cache, K key, V value){
        log.info("主动更新缓存, key:"+key+", value:"+value);
        cache.put(key, value);
    }

}
