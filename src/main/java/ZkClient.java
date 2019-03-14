import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class ZkClient {
    private static final String connectString="10.118.150.23:2181";
    private static final int sessionTimeout=20000;
    private ZooKeeper zooKeeper;
    
    //创建ZK
    private void createZKInstance() throws IOException
    {
       zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getZkChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    //增加节点
    private void createNode() throws KeeperException, InterruptedException {
        zooKeeper.create("/zkTest","aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
    }

    //查询数据结构
    private void getZkChildren() throws KeeperException, InterruptedException {
        List<String> getChildren = zooKeeper.getChildren("/zkTest", true);
        System.out.println(getChildren.toString());
    }

    //查询数据
    private void getData() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/zkTest", true, new Stat());
        System.out.println(new String(data));
    }

    //添加数据
    private void setData() throws KeeperException, InterruptedException {
        zooKeeper.setData("/zkTest","bbb".getBytes(),-1);
        zooKeeper.setData("/zkTest","ccc".getBytes(),-1);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZkClient zk = new ZkClient();
        zk.createZKInstance();
        zk.getZkChildren();
//        zk.createNode();
//        zk.setData();
        Thread.sleep(Long.MAX_VALUE);
    }
}
