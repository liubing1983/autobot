package com.lb.book.thread.p06;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by samsung on 2017/8/30.
 */
public class CompletionServiceTest {

    private static final ExecutorService execs = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        CompletionService<Boolean> cs = new ExecutorCompletionService<Boolean>(execs);

        for (int i = 0; i < 10; i++) {
            cs.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Random r = new Random();
                    Boolean b = r.nextBoolean();
                    System.out.println(Thread.currentThread().getName() + "---" + b);
                    Thread.sleep(5000);
                    return b;
                }
            });
        }

        try {
            while (true) {
                Future<Boolean> f = cs.take();
                Boolean b = f.get();
                System.out.println(b);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            execs.shutdown();
        }

    }
}
