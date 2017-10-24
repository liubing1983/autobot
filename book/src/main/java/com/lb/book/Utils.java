package com.lb.book;

/**
 * Created by samsung on 2017/9/25.
 */
public class Utils {

    public static void launderThrowable(Throwable  t){
        if(t instanceof RuntimeException)
            throw  (RuntimeException)t;
        else if(t instanceof Error)
            throw (Error)t;
        else throw new RuntimeException(t);
    }
}
