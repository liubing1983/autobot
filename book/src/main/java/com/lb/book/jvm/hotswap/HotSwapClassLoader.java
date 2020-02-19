package com.lb.book.jvm.hotswap;

/**
 * @ClassName HotSwapClassLoader
 * @Description @TODO
 * @Author liubing
 * @Date 2020/2/10 09:47
 * @Version 1.0
 **/
public class HotSwapClassLoader  extends  ClassLoader{

    public HotSwapClassLoader(){

        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte){
        return defineClass(null, classByte, 0, classByte.length);
    }

}
