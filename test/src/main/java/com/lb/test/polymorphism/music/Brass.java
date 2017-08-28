package com.lb.test.polymorphism.music;

/**
 * Created by liub on 2016/12/13.
 */
public class Brass extends Instrument {

    public  void play(Note n){
        System.out.println("Brass.play : " + n);
    }
}
