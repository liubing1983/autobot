package com.lb.test.io;

/**
 * Created by liub on 2016/12/9.
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class TestSerializable {

    // 必须实现Serializable接口才能够被序列化和反序列化
    static class MyClass implements Serializable {
        private static final long serialVersionUID = 2973323348198097844L;

        private int a, b; // 一般的实例变量会被序列化和反序列化
        private transient int c; // transient实例变量 不会 被序列化和反序列化
        private static int d; // 类变量 不会 被序列化和反序列化

        public MyClass() {
        }

        public MyClass(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            MyClass.d = d;
        }

        @Override
        public String toString() {
            return this.a + "  " + this.b + "  " + this.c + "  " + MyClass.d;
        }
    }

    public static void serialize(String fileName) throws Exception {// 序列化对象到文件
        // 创建一个对象输出流，将对象输出到文件
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject("Today:"); // 序列化一个字符串对象到文件
        out.writeObject(new Date());// 序列化当前日期对象到文件
        MyClass my1 = new MyClass(5, 6, 7, 8);// 序列化一个MyClass对象
        out.writeObject(my1);
        out.close();
    }

    public static void deserialize(String fileName) throws Exception {// 从文件反序列化到对象
        // 创建一个对象输入流，从文件读取对象
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        // 注意读对象时必须按照序列化对象时的顺序读，否则会出错
        String today = (String) (in.readObject());// 读取字符串对象
        System.out.println(today);
        Date date = (Date) (in.readObject());// 读日期对象
        System.out.println(date.toString());
        MyClass my1 = (MyClass) (in.readObject());// 读MyClass对象，并调用它的add方法
        System.out.println(my1.toString());
        in.close();
    }

    public static void main(String[] args) throws Exception {
        String fileName = "D:/poem.txt";
        //serialize(fileName);
        // 注释掉第二行，只运行下面一行，将会发现输出不同
        deserialize(fileName);
    }
}

/**
 * 如果某个类能够被序列化，其子类也可以被序列化。声明为static和transient类型的成员数据不能被序列化。因为static代表类的状态，transient代表对象的临时数据。

 类通过实现java.io.Serializable接口以启用其序列化功能。未实现此接口的类将无法使其任何状态序列化。可序列化类的所有子类型本身都是可序列化的。序列化接口没有方法或字段，仅用于标识可序列化的语义。

 要允许不可序列化类的子类型序列化，可以假定该子类型负责保存和还原超类型的公用（public）、受保护的（protected）和（如果可访问）包（package）字段的状态。仅在子类型扩展的类有一个可访问的无参数构造方法来初始化该类的状态时，才可以假定子类型有此责任。如果不是这种情况，则声明一个类为可序列化类是错误的。该错误将在运行时检测到。
 */
