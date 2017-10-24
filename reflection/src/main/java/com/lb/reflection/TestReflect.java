package com.lb.reflection;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liub on 2016/12/3.
 */
public class TestReflect {

    public static void main(String[] args) throws Exception {

        // 初始化类
        Class c = Class.forName("com.lb.reflection.HelloWorld");
        System.out.println("包名: " + c.getPackage());
        System.out.println("类名: " + c.getName());

        //  ======================  构造函数  ===========================

        // 获取构造函数
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        // 通过参数获取构造函数, 如果不存在则抛出NoSuchMethodException
        // Constructor constructor = c.getConstructor(new Class[]{String.class, String.class});
        //System.out.println(constructor);


        //利用Constructor对象实例化一个类
        Constructor constructor_hw = HelloWorld.class.getConstructor(String.class);
        HelloWorld myObject = (HelloWorld) constructor_hw.newInstance("constructor-arg1");
        myObject.printName();


        //  ======================  对象  ===========================
        // 获取全部对象
        Field[] fields = c.getFields();
        for (Field field : fields) {
            // 得到声明 类型 路径.变量名称
            System.out.println(field);
            // 得到变量名称
            System.out.println(field.getName());
            // 得到类型
            System.out.println(field.getType());
            // 给变量赋值  取值
            HelloWorld a = new HelloWorld();
            field.set(a, "123");
            System.out.println(field.get(a));
        }

        // 获取已知的变量名
        Field field = c.getField("field_name");
        System.out.println(field);
        System.out.println("==============================================");

        //  ======================  方法  ===========================
        // 全部方法
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            System.out.println(method);
            System.out.println(method.getParameters().length+"---");
        }

        // 获取已知的方法
        Method method = c.getMethod("printName", null);
        System.out.println(method);

        System.out.println("==============================================");

        //  ======================  方法  ===========================


        System.out.println("==============================================");

        //  ======================  泛型  ===========================
        Field field1 = c.getField("list");
        System.out.println(field1.getType());
    }
}
