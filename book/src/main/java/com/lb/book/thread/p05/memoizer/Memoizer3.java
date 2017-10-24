package com.lb.book.thread.p05.memoizer;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 方案三: 使用FutuerTask
 * Created by samsung on 2017/9/25.
 */
public class Memoizer3<A, V> implements Computable<A, V>{

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();

    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c){
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        // 从缓存中查找数值
        Future<V> f = cache.get(arg);
        /**
         * 存在漏洞, 当相同的计算同时调用时, 还是会发生重复调用
         */
        // 如果没有缓存计算数据
        if(f == null){
            // 使用闭锁, 等待前一个计算完成
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return  c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;

            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        } catch (ExecutionException e) {

        }
        return null;
    }
}
