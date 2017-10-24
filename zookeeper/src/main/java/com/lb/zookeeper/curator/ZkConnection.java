package com.lb.zookeeper.curator;

/**
 * Created by liub on 2016/12/8.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkConnection {

    Logger logger = LoggerFactory.getLogger(ZkConnection.class);

    private String namespace;
    private String ip;

    // 超时时间
    private int sleep;
    // 重试次数
    private int num;

    /**
     * @param namespace 命名空间
     * @param ip        zk server IP
     */
    public ZkConnection(String namespace, String ip) {
        this.namespace = namespace;
        this.ip = ip;
        this.sleep = 300;
        this.num = 3;
    }


    /**
     * @param namespace
     * @param ip
     * @param sleep
     * @param num
     */
    public ZkConnection(String namespace, String ip, int sleep, int num) {
        this.namespace = namespace;
        this.ip = ip;
        this.sleep = sleep;
        this.num = num;
    }

    /**
     * 创建zookeeper连接
     *
     * @return
     */
    public CuratorFramework getZKConnection() {

        // 重试策略  （sleep时间， 重试次数）
        RetryPolicy rp = new ExponentialBackoffRetry(sleep, num);
        // 创建zookeeper连接
        if (StringUtils.isBlank(namespace)) {
            return CuratorFrameworkFactory.builder().connectString(ip).sessionTimeoutMs(5000).retryPolicy(rp).build();
        } else {
            return CuratorFrameworkFactory.builder().connectString(ip).sessionTimeoutMs(4000).retryPolicy(rp).namespace(namespace).build();
        }
    }

    public static void main(String[] args) {
        ZkConnection zkc = new ZkConnection("lb", "10.95.3.136:2181,10.95.3.138:2181");
        CuratorFramework client = zkc.getZKConnection();
        client.start();

        client.close();
    }
}
