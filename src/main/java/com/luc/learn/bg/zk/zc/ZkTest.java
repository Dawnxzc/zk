package com.luc.learn.bg.zk.zc;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author xuzhichao
 * @date 2019/3/20 10:43
 * @Description:
 */
public class ZkTest {

    private static final String connectionString = "116.62.157.220:2181";

    private static final int sessionTimeout = 10000;

    private ZooKeeper zooKeeper = null;

    @Before
    public void getZkConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectionString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                String path = watchedEvent.getPath();
                System.out.println(watchedEvent);
            }
        });
    }

    @Test
    public void testGet() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/zkTest", true);
        System.out.println(children);
    }

    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        String node1 = zooKeeper.create("/zkTest/servers", "servers".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(node1);
    }

    @Test
    public void testDel() throws KeeperException, InterruptedException {
        String parentPath = "/zkTest/servers";
        List<String> children = zooKeeper.getChildren(parentPath, true);
        if (null != children && !children.isEmpty()){
            for (String node : children){
                zooKeeper.delete(parentPath + "/" + node, -1);
            }
        }
    }

    /**
     * 模拟server1启动/停止
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void server1() throws KeeperException, InterruptedException {
        zooKeeper.create("/zkTest/servers/server01", "servers".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 模拟server2启动/停止
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void server2() throws KeeperException, InterruptedException {
        zooKeeper.create("/zkTest/servers/server02", "servers".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 模拟server3启动/停止
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void server3() throws KeeperException, InterruptedException {
        zooKeeper.create("/zkTest/servers/server03", "servers".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Long.MAX_VALUE);
    }
}
