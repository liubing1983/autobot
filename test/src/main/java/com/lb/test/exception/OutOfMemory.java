package com.lb.test.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liub on 2017/2/7.
 */
public class OutOfMemory {

    //   Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

    public  static void main(String[] args){
       List l = new ArrayList();
        for(int i = 0; i<1000000000; i++){
            int[] o = new int[1000000];
            l.add(o);
        }
    }
}
