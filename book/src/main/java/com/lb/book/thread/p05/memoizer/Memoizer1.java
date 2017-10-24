package com.lb.book.thread.p05.memoizer;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;

/**
 * 第一种方案: 使用HashMap来存储缓存  P86
 * Created by samsung on 2017/9/25.
 */
public class Memoizer1<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public Memoizer1(Computable<A,V> c){this.c = c; }

    /**
     * 第一种方案, 因为HashMap不是线程安全的, 所以需要对整个comput方法进行同步.
     *  如果comput方法执行时间很长, 那么其他调用comput的线程可能被阻塞很长时间.
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result == null){
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
