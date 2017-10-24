package com.lb.book.thread.p07;

import java.util.concurrent.TimeUnit;

import static com.lb.book.Utils.launderThrowable;

/**
 * Created by samsung on 2017/9/26.
 */
public class TimeRunInterrupt {

    public static void timeRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException{
        class RethrowableTask implements Runnable{
            private volatile  Throwable t;
            public void run(){
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }

               // void rethrow(){
                   // if(t != null) throw  launderThrowable(t);
         //   }
            }
        }
    }

}
