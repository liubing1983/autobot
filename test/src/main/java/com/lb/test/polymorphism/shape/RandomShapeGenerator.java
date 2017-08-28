package com.lb.test.polymorphism.shape;

import java.util.Random;

/**
 * Created by liub on 2016/12/13.
 */
public class RandomShapeGenerator {

    private Random rand = new Random(47);
    public Shape next(){
        switch (rand.nextInt(2)){
            case 0: return new Circle();
            case 1: return new Square();
            default: return new Shape();
        }
    }

}
