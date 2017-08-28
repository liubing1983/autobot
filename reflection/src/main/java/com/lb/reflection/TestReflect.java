package com.lb.reflection;

/**
 * Created by liub on 2016/12/3.
 */
public class TestReflect {

    public static void main(String[] args) throws  Exception{

        // 初始化类
        Class c = Class.forName("com.lb.zookeeper.curator.crud.Demo");
        System.out.println("包名: "+c.getPackage());
        System.out.println("类名: "+c.getName());

    }
}
