package com.lb.reflection;

/**
 * Created by liub on 2016/12/3.
 */
public class Demo {

    public static void main(String[] args){
        System.out.println("000000000000000");
        try {
            Class.forName("com.lb.zookeeper.curator.crud.Demo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
