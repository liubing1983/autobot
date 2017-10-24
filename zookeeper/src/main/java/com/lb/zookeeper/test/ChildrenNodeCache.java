package com.lb.zookeeper.test;

import com.lb.zookeeper.curator.ZkConnection;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明:
 * Created by LiuBing on 2017/10/19.
 */
public class ChildrenNodeCache {
    private static Logger log = LoggerFactory.getLogger(ChildrenNodeCache.class);
    //  java -cp zookeeper-1.0-SNAPSHOT.jar:log4j.jar:slf4j-api.jar:slf4j-log4j12.jar:curator-framework.jar:curator-recipes.jar:zookeeper.jar:curator-client.jar:commons-lang.jar:guava-18.0.jar   com.lb.zookeeper.test.ChildrenNodeCache   adsurvey   Adsurvey-namenode1:2181,Adsurvey-namenode2:2181,datanode1:2181

    public static void main(String[] args) {

        String path = "/";

        // 创建连接
        //  ZkConnection zkc = new ZkConnection("adsurvey", "Adsurvey-namenode1:2181,Adsurvey-namenode2:2181,datanode1:2181");
        //ZkConnection zkc = new ZkConnection("adsurvey", "10.170.177.186:2181,10.173.19.100:2181,10.172.234.212:2181");
        ZkConnection zkc = new ZkConnection(args[0], args[1]);
        CuratorFramework client = zkc.getZKConnection();


        client.start();
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        try {
            // cache.start(StartMode.POST_INITIALIZED_EVENT);
            cache.start();
        } catch (Exception e) {
            log.error("监控启动异常!!", e);
            e.printStackTrace();
        }
        log.info("监控已经开启,  path: /" + args[0]+"/");
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        log.info("事件类型：" + event.getType() + ", 节点名称" + new String(event.getData().getData()) + ", 节点路径:" + event.getData().getPath() + ", 节点详细信息：" + event.getData().getStat().toString() + ", " + System.currentTimeMillis());
                        //System.out.println("新增");
                        break;
                    case CHILD_UPDATED:
                        log.info("事件类型：" + event.getType() + ", 节点名称" + new String(event.getData().getData()) + ", 节点路径:" + event.getData().getPath() + ", 节点详细信息：" + event.getData().getStat().toString() + ", " + System.currentTimeMillis());
                        //System.out.println("修改");
                        break;
                    case CHILD_REMOVED:
                        log.info("事件类型：" + event.getType() + ", 节点名称" + new String(event.getData().getData()) + ", 节点路径:" + event.getData().getPath() + ", 节点详细信息：" + event.getData().getStat().toString() + ", " + System.currentTimeMillis());
                        //System.out.println("删除");

                        break;
                    default:
                        break;
                }
            }
        });

        // 循环保证线程不退出
        try {
            while (true) {
                Thread.sleep(60000);
                //log.info("---------------------------------  while "+ System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("while error!!!!!!!!!", e.getMessage());
        } finally {
            client.close();
            log.error("while close!!!!!!!!!   " + System.currentTimeMillis());
        }

    }


}
