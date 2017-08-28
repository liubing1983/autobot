package com.lb.test.exception;

/**
 * Created by liub on 2017/2/7.
 */
public class StackOverflow {

    public static void ab(Long a){

        if(a>=10000000){

        }else{
            ab(a+1);
        }

    }

    public static void  main(String[] args){
        StackOverflow.ab(1L);
    }

}
