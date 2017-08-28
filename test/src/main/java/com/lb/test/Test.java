package com.lb.test;

/**
 * Created by liub on 2016/12/21.
 */
public class Test {

    public static void main(String[] args){
        int i = 15;
        i = i++;
        // i++,以j=i++;为例在底层的实现是：temp = i;i = i + 1; j = temp;
        // 所以，i=15;i=i++;这个表达式的结果是15.（因为加一之后又执行了一次赋值，从16变回15）
        System.out.println(i);

        int j = 2;
        System.out.println(i+++j);

        System.out.println((double)0.1+(double)0.2);

        int m = +0;
        int n = -0;
        System.out.println(m==n);

        B b  = new B();
        C c = new C();

        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;

        System.out.println(f1 == f2);
        System.out.println(f3 == f4);

        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());
    }
}

class A{
    A(){
        System.out.println("--a");
    }

    public void a (){
        System.out.println("a");
    }
}

class B extends A{
    B(){
        System.out.println("--b");
    }
    public void a(){
        System.out.println("b");
    }
}

class C extends A{
    C(){
        System.out.println("--c");
    }
}


