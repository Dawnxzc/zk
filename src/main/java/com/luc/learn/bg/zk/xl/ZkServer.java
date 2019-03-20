package com.luc.learn.bg.zk.xl;

import org.apache.zookeeper.*;

import java.io.IOException;


public class ZkServer {

    private static final String connectString="10.118.150.23:2181";
    private static final int sessionTimeout=20000;
    private ZooKeeper zooKeeper;

    //创建ZK
    private void createZKInstance() throws IOException
    {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    //增加节点
    private void createNode() throws KeeperException, InterruptedException {
        zooKeeper.create("/zkTest/node5","aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);
    }

    //删除节点
    private void deleteNode() throws KeeperException, InterruptedException {
        zooKeeper.delete("/zkTest/node5",-1);
    }


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZkServer zk = new ZkServer();
        zk.createZKInstance();
        zk.createNode();
    }
}
