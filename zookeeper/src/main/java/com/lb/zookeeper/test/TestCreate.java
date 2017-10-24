package com.lb.zookeeper.test;

import com.lb.zookeeper.curator.ZkConnection;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static sun.misc.Version.println;

/**
 * 说明:
 * Created by LiuBing on 2017/10/19.
 */
public class TestCreate {

    // java -cp zookeeper-1.0-SNAPSHOT.jar  com.lb.zookeeper.test.TestCreate  adsurvey   Adsurvey-namenode1:2181,Adsurvey-namenode2:2181,datanode1:2181
//  java -cp zookeeper-1.0-SNAPSHOT.jar:log4j.jar:slf4j-api.jar:slf4j-log4j12.jar:curator-framework.jar:curator-recipes.jar:zookeeper.jar:curator-client.jar:commons-lang.jar:guava-18.0.jar  com.lb.zookeeper.test.TestCreate  adsurvey   Adsurvey-namenode1:2181,Adsurvey-namenode2:2181,datanode1:2181
    private static Logger logger = LoggerFactory.getLogger(TestCreate.class);

    public static void main(String[] args) {
        try {
            logger.info("111" + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // 创建连接
      // ZkConnection zkc = new ZkConnection("adsurvey", "datanode01:2181,datanode02:2181,datanode03:2181,datanode04:2181");
        ZkConnection zkc = new ZkConnection(args[0], args[1]);
        CuratorFramework client = zkc.getZKConnection();

        logger.info("222");
        // 启动
        client.start();
        logger.info("333");
        // 创建临时节点
        try {
            client.create().withMode(CreateMode.EPHEMERAL).
                    forPath("/test/test_watcher", ("test_watcher - " + System.currentTimeMillis()).getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("create error!!!!!!!!!!!", e.getMessage());
            client.close();
            System.exit(1);
        }
        logger.info("init");

        // 循环保证线程不退出
        try {
            while (true) {
                Thread.sleep(60000);
                logger.info("---------------------------------  while "+ System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("while error!!!!!!!!!", e.getMessage());
        } finally {
            client.close();
            logger.error("while close!!!!!!!!!   " + System.currentTimeMillis());
        }
    }

}
