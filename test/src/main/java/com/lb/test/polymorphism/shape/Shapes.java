package com.lb.test.polymorphism.shape;

/**
 * Created by liub on 2016/12/13.
 */
public class Shapes {

    public static RandomShapeGenerator rs = new RandomShapeGenerator();

    public void printlns(){
        System.out.println("Shapes  printlns ");
    }

    public static void main(String[] args){
        Shape[] s = new Shape[9];

        for(int i = 0; i< s.length; i++){
            s[i] = rs.next();
        }
        for(Shape ss :s ){
            ss.draw();
        }
    }
}
