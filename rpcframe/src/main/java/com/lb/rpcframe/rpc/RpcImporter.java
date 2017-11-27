package com.lb.rpcframe.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 说明:
 * Created by LiuBing on 2017/11/23.
 */
public class RpcImporter<S> {

    public S importer(final Class<?> serviceClass, final InetSocketAddress addr) {
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{
                        serviceClass.getInterfaces()[0]
                },
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = null;
                        ObjectOutputStream output = null;
                        ObjectInputStream input = null;

                        try {
                            // 创建连接
                            socket = new Socket();
                            socket.connect(addr);
                            // 获取输出流
                            output = new ObjectOutputStream(socket.getOutputStream());
                            // 写入数据项
                            // 接口名称
                            output.writeUTF(serviceClass.getName());
                            // 方法名称
                            output.writeUTF(method.getName());

                            // 返回值类型
                            output.writeObject(method.getParameterTypes());
                            // 方法参数
                            output.writeObject(args);

                            // 返回值
                            input = new ObjectInputStream(socket.getInputStream());
                            return input.readObject();
                        } finally {
                            if (socket != null) socket.close();
                            if (output != null) output.close();
                            if (input != null) input.close();
                        }
                    }
                });
    }
}
