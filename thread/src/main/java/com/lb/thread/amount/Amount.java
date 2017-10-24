package com.lb.thread.amount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by samsung on 2017/9/5.
 */
public class Amount {

     AtomicLong  a = new AtomicLong(10000);

     // JDK8 提供的新原子变量
     LongAdder b = new LongAdder();
     // 无法保证最终结果
     Long c = 10000L;


    ExecutorService execs = Executors.newFixedThreadPool(100);

    private  void add() {
        Runnable r = new Runnable() {
                @Override
                public void run() {
                    //a.addAndGet(1);
                   // b.add(1);
                    c ++;
                    try {
                        TimeUnit.SECONDS.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"  -  "+c);
                }
            };
        execs.submit(r);
    }

    private  void del() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
              //  a.decrementAndGet();
                // b.decrement();
                c--;
                try {
                    TimeUnit.SECONDS.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"  -  "+c);

            }
        };
        execs.submit(r);
    }


    public static void main(String[] args) {
        Amount amount = new Amount();
        for (int i = 0; i <= 1000; i++) {
            amount.add();
            amount.del();
        }
         amount.execs.shutdown();
    }

}
