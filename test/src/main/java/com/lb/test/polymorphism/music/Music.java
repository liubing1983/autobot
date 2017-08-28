package com.lb.test.polymorphism.music;

/**
 * Created by liub on 2016/12/13.
 */
public class Music {

    public static void tune(Instrument i){
        i.play(Note.LB);
    }

    public static void main(String[] args){
        Wind w = new Wind();
        Brass b = new Brass();
        tune(w);
        tune(b);

        Instrument i = new Wind();
        tune(i);



        System.out.println(w.qwe);
        System.out.println(i.qwe);
    }
}
