package com.lb.book.thread.p05.memoizer;

import java.math.BigInteger;

/**
 * Created by samsung on 2017/9/25.
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }
}
