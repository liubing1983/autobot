package com.lb.book.thread.p02;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by samsung on 2017/8/25.
 */
public class Number {

    // 访问计数器
     private Long count = 0L;
    private final AtomicLong atomiccount = new AtomicLong(0);

    public long getCount() {
        return count;
    }

    /**
     * 返回访问数
     * @return
     */
    public long getAtomicCount() {
        return atomiccount.get();
    }

    /**
     * 模拟被访问程序
     * @param i
     * @param j
     * @return
     */
    public int add(int i, int j) {
        System.out.println(atomiccount.get());
        ++count;

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i + j;
    }

    public int addThread(int i, int j) {
        System.out.println(atomiccount.get());
        atomiccount.incrementAndGet();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i + j;
    }

}
