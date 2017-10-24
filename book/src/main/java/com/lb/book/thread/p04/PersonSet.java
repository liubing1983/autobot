package com.lb.book.thread.p04;

import java.util.HashSet;
import java.util.Set;

/**
 * 通过屏蔽机制来确保线程安全  P50
 * Created by samsung on 2017/9/1.
 */
// @ThreadSafe
public class PersonSet {

    /**
     * HaseSet本身不是线程安全的
     * 将他的状态封装到PersonSet中, 实现线程安全
     */
    private final Set<Person> mySet = new HashSet<Person>();

    // 加锁后才能添加对象
    public synchronized void addPerson(Person p){
        mySet.add(p);
    }

    // 加锁后才能获取对象的状态
    public synchronized  boolean containsPerson(Person p){
        return mySet.contains(p);
    }

}

class Person{

}
