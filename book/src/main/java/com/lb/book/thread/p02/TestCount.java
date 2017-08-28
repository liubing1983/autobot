package com.lb.book.thread.p02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by samsung on 2017/8/25.
 */
public class TestCount {
    static ExecutorService exec = Executors.newFixedThreadPool(10);

    Number n = new Number();

    /**
     * 顺序访问函数, 计数器无异常
     */
    private void testAddCount() {
        for (int i = 0; i < 10; i++) {
            System.out.println(n.add(i, i) + "  --  " + n.getCount());
        }
    }


    private void testAddCountThread() {
        int a = 0;
        while (true) {
            ++a;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    System.out.println(n.add(1, 1) +"--"+n.getCount());
                   System.out.println(n.addThread(1, 1) +"-=-=-="+n.getAtomicCount());
                }
            };
            exec.execute(new Thread(r));

            //System.out.println(n.getCount() + "---");`
            if (a >= 10) break;
        }
        exec.shutdown();
    }

    public static void main(String[] args) {
        TestCount t = new TestCount();
        t.testAddCountThread();
    }

}
