package com.lb.book.thread.p05.memoizer;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第二章方案: 使用ConcurrentHashMap来存储缓存  P87
 * Created by samsung on 2017/9/25.
 */
public class Memoizer2<A, V> implements  Computable<A, V> {


    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A,V> c){this.c = c;  }

    /**
     * 第二种方案, ConcurrentHashMap是线程安全的, 可以提高并发性.
     *  但是ConcurrentHashMap可能会使数据重复计算.
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public  V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result == null){
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
