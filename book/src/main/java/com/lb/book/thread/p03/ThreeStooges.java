package com.lb.book.thread.p03;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liub on 2017/2/21.
 */
public final class ThreeStooges {

    // 不可变对象内部仍可用可变对象来管理他们的状态.
    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges(){
        stooges.add("A");
        stooges.add("B");
        stooges.add("C");
    }

    public boolean isStooges(String name){
        stooges.add("E");
        return stooges.contains(name);
    }

    public static void main(String[] args){
        ThreeStooges t = new ThreeStooges();
        System.out.println(t.isStooges("A"));
        System.out.println(t.isStooges("E"));
    }
}
