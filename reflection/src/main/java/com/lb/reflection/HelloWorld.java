package com.lb.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samsung on 2017/9/19.
 */
public class HelloWorld {

    private   String name;
    private int age;

    public String field_name = "lllll";

    List<Integer> list = new ArrayList<Integer>();

    public HelloWorld(){}

    public HelloWorld(String name){
        this.name = name;
        this.age = 20;
    }

    public HelloWorld(String name, int age){
        this.name = name;
        this.age = age;
    }


    public void printName(){
        System.out.println("Hello, World! "+name);
    }

    public static void main(String[] args){

    }
}
