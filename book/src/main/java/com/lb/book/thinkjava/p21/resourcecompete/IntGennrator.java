package com.lb.book.thinkjava.p21.resourcecompete;

/**
 * Created by liub on 2017/3/5.
 */
public abstract class IntGennrator {

    private volatile  boolean canceled = false;

    public abstract  int next();

    public void cancel(){  canceled  =true;  }

    public boolean isCanceled(){return canceled;}
}
