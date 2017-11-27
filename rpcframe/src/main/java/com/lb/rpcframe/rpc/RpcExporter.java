package com.lb.rpcframe.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 说明:
 * Created by LiuBing on 2017/11/23.
 */
public class RpcExporter {

    Logger log = LoggerFactory.getLogger(RpcExporter.class);

    // 调度方式
    static ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void exporter(String hostname, int port) throws Exception {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(hostname, port));

        try {
            while (true) {
                exec.execute(new ExporterTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }


    private static class ExporterTask implements Runnable {
        Logger log = LoggerFactory.getLogger(RpcExporter.class);
        Socket client = null;

        public ExporterTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;

            try {
                /**
                 * 从socket中获得输入流
                 * 与RpcImporter中 ObjectOutputStream 的写入顺序一致
                 */
                input = new ObjectInputStream(client.getInputStream());

                // 接口名称
                String interfaceName = input.readUTF();
                log.debug("interfaceName : " + interfaceName);
                // 得到接口的class对象
                Class<?> service = Class.forName(interfaceName);
                log.debug("service : " + service.getName());

                // 得到方法名称
                String methodName = input.readUTF();
                log.debug("methodName : " + methodName);
                Class<?>[] paramenterTypes = (Class<?>[]) input.readObject();
                Object[] args = (Object[]) input.readObject();
                log.debug("args start.");
                for (Object o : args) {
                    log.debug(o.toString());
                }
                log.debug("args end!!!");
                Method method = service.getMethod(methodName, paramenterTypes);
                Object result = method.invoke(service.newInstance(), args);

                log.debug("result : "+result.toString());
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null) output.close();
                    if (input != null) input.close();
                    if (client != null) client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
