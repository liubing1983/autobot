package com.lb.zookeeper.curator.watcher;

/**
 * Created by liub on 2016/12/7.
 */

import com.lb.zookeeper.curator.ZkConnection;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class ChildrenDemo extends Observable {

    //  java -cp zookeeper-1.0-SNAPSHOT.jar:log4j.jar:slf4j-api.jar:slf4j-log4j12.jar:curator-framework.jar:curator-recipes.jar:zookeeper.jar:curator-client-3.2.1.jar:commons-lang-2.6.jar:guava-18.0.jar   com.lb.zookeeper.curator.watcher.ChildrenDemo

    Logger log = Logger.getLogger(ChildrenDemo.class);
    static String path = "/test";

    // 创建连接
    //  ZkConnection zkc = new ZkConnection("adsurvey", "Adsurvey-namenode1:2181,Adsurvey-namenode2:2181,datanode1:2181");
    ZkConnection zkc = new ZkConnection("adsurvey", "datanode01:2181,datanode02:2181,datanode03:2181,datanode04:2181");
    CuratorFramework client = zkc.getZKConnection();

    // 创建线程池
    private Executor executor = Executors.newSingleThreadExecutor();

    FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
        public Integer call() throws InterruptedException {
            ChildrenWatch();
            return Integer.valueOf(0);
        }
    });

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }

    /**
     * 启动监控过程
     */
    public void execute() {
        // 通过线程池启动一个额外的线程加载Watching过程
        executor.execute(task);
    }

    /**
     * 关闭后的对象无法重新启动
     *
     * @throws IOException
     */
    public void shutdown() throws IOException {
        executor = null;
    }

    /**
     * 监控cheng
     */
    public void ChildrenWatch() {
        // 启动
        client.start();
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        try {
           // cache.start(StartMode.POST_INITIALIZED_EVENT);
            cache.start();
        } catch (Exception e) {
            log.error("监控启动异常!!", e);
            e.printStackTrace();
        }
        log.info("监控已经开启！！");
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                log.info("---------------------" + event.toString() + "---" + Thread.currentThread().getName());
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
    }

    public static void main(String[] args) {
        ChildrenDemo cd = new ChildrenDemo();
        cd.execute();
    }

}
