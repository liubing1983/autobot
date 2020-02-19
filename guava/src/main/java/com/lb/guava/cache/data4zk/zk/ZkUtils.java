package com.lb.guava.cache.data4zk.zk;

import com.lb.zookeeper.curator.ZkConnection;
import org.apache.curator.framework.CuratorFramework;

public class  ZkUtils {

    private static  ZkConnection zkc = null;
    private static CuratorFramework client = null;

    public CuratorFramework getCuratorFrameworkClient(){
        if(client == null){
            this.zkc = new ZkConnection("lb", "127.0.0.1:2181");
            this.client = zkc.getZKConnection();

        }
        return client;
    }

    public <V> V finkZk(CuratorFramework client, String key) throws  Exception{
        return (V)new String(client.getData().forPath("/guavacache/"+key));
    }


    public void close(){
        client.close();
    }
}
