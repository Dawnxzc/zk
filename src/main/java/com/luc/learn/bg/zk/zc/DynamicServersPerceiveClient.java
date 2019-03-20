package com.luc.learn.bg.zk.zc;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态服务感知 Client
 * @author xuzhichao
 * @date 2019/3/20 11:13
 * @Description:
 */
public class DynamicServersPerceiveClient {

    /**
     * 服务器列表
     */
    private volatile List<String> serverList = new ArrayList<String>();

    /**
     * zk连接
     */
    private static final String ZK_HOST = "116.62.157.220:2181";

    /**
     * 连接超时时间
     */
    private static final int SESSION_TIMEOUT = 10000;

    /**
     * 服务监听节点
     */
    private static final String serverPath = "/zkTest/servers";

    private ZooKeeper zooKeeper = null;

    public void connectZookeeper() throws IOException {
        zooKeeper = new ZooKeeper(ZK_HOST, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 获取事件类型
                Event.KeeperState eventState = watchedEvent.getState();
                if (eventState == Event.KeeperState.Disconnected || eventState == Event.KeeperState.SyncConnected){
                    try {
                        // 刷新服务列表
                        refreshServers();

                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 重新获取服务列表
     */
    private void refreshServers() throws KeeperException, InterruptedException {
        serverList = zooKeeper.getChildren(serverPath, true);
        System.out.println(serverList);
    }

    private void getServers() throws KeeperException, InterruptedException {
        zooKeeper.getChildren(serverPath, true);
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 开启客户端
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void startClient() throws IOException, InterruptedException, KeeperException {
        connectZookeeper();
        Thread.sleep(10000);
        getServers();
    }


}
