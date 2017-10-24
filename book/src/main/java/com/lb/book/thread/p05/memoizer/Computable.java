package com.lb.book.thread.p05.memoizer;

/**
 * 实现一个同步缓存  P86
 * 声明一个接口, 输入为A, 输出为V
 * Created by samsung on 2017/9/25.
 */
public interface Computable <A, V>{

    V compute(A arg) throws InterruptedException;
}
