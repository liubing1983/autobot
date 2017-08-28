package com.lb.book.thinkjava.p21.resourcecompete;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liub on 2017/3/5.
 */
public class EvenGennrator extends IntGennrator {
    private  int i  = 0;
    AtomicInteger ai = new AtomicInteger(0);
    // @Override
    //  加上synchronized关键字防止资源共享
    public  synchronized int next1() {
   // public   int next() {
        ai.decrementAndGet();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ai.decrementAndGet();;
        return ai.get();
    }

    Lock lock = new ReentrantLock();

    @Override
    public   int next() {

            ++i;
            Thread.yield();
            ++i;

        return i;
    }

    public  int next2() {
        lock.lock();
        try {
            ++i;
            Thread.yield();
            ++i;
        } finally {
            lock.unlock();
        }
        return i;
    }

    public static void main(String[] args){
        EvenChecker.test(new EvenGennrator());
    }
}
