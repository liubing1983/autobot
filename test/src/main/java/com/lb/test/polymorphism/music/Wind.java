package com.lb.test.polymorphism.music;

/**
 * Created by liub on 2016/12/13.
 */
public class Wind extends Instrument {

    public  int qwe = 1;

    public  void play(Note n){
        System.out.println("Wind.play : " + n);
    }
}
