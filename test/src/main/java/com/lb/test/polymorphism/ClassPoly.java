package com.lb.test.polymorphism;

/**
 * Created by liub on 2016/12/12.
 */
public class ClassPoly  implements InterfacePoly {

    public String classp(){
        return "1";
    }


    public String classf(){
        return "classf";
    }


    @Override
    public String lb(String name) {
        return null;
    }
}
