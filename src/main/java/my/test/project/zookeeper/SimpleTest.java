package my.test.project.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.logging.Logger;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/10/14 19:25
 * @Modified by:
 */
public class SimpleTest {
    public static Logger logger = Logger.getLogger("SimpleTest.class");

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 15000, null);

        zk.create("/simple", "123".getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int i, String s, Object o, String s1) {
                logger.info("i = " + KeeperException.Code.get(i) + " , s = " + s + " ,o=" + o + " ,s1 = " + s1); // i = OK , s = /simple ,o=task object ,s1 = /simple
            }
        }, "task object");

        AsyncCallback.StatCallback callback = new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int i, String s, Object o, Stat stat) {
                for (int a = 1; a < 3; a++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info(KeeperException.Code.get(i) + "|" + o + ": " + String.valueOf(a));
                }
            }
        };

        zk.exists("/simple", null, callback, "context1");
        System.out.println("node 1 done");
        zk.exists("/simple", null, callback, "context2");
        System.out.println("node 2 done");
        zk.exists("/simple", null, callback, "context3");
        System.out.println("node 3 done");


        System.in.read();
    }

}
