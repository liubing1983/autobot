package com.lb.book.thinkjava.p5;

/**
 * Created by liub on 2016/12/7.
 */
public class Leaf {

    int i  = 0;
    Leaf incr(){
        i ++;
        return new Leaf();   //  打印0
        //  return this;     //  打印3
    }

    void  print(){
        System.out.println("i == "+i);
    }

    public static void main(String[] args){
        Leaf l = new Leaf();
        l.incr().incr().incr().print();
    }
}
