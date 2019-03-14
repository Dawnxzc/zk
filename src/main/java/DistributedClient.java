import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributedClient {

    private static final String connectString="10.118.150.23:2181";
    private static final int sessionTimeout=20000;
    private CountDownLatch latch = new CountDownLatch(1);

    private ZooKeeper zooKeeper;
    private String thisPath;
    private String waitPath;


    //创建ZK
    private void createZKInstance() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    latch.countDown();
                }
                if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)) {
                    //TODO
                }
            }
        });

        latch.await();

    }
    //增加节点
    private void createNode() throws KeeperException, InterruptedException {
        zooKeeper.create("/zkTest/node1",null, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        Thread.sleep(10);
    }

    //判断锁情况
    private void lock() throws KeeperException, InterruptedException {
        List<String> childrenList = zooKeeper.getChildren("/zkTest", false);
        if (childrenList.size()==1){
            //TODO
        }else {
            Collections.sort(childrenList);
            if (childrenList.indexOf("node1")==-1){

            }else if (childrenList.indexOf("node1")==0){
                //TODO
            }else {

            }
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        for(int i = 0;i<10;i++){

        }
        Thread.sleep(Long.MAX_VALUE);

    }

}
