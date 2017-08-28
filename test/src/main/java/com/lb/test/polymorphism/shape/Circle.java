package com.lb.test.polymorphism.shape;

/**
 * Created by liub on 2016/12/13.
 */
public class Circle extends Shape {
    @Override
    public void draw(){ System.out.println(" Circle.draw  ");}

    @Override
    public void erase(){System.out.println(" Circle.erase  ");}
}
