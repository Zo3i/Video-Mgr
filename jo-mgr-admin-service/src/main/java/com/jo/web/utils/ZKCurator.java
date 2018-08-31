package com.jo.web.utils;


import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZKCurator {

    private CuratorFramework client = null;

    final static Logger log = LoggerFactory.getLogger(ZKCurator.class);

    private ZKCurator(CuratorFramework client) {
        this.client = client;
    }
    private void init() {
        client = client.usingNamespace("admin");
        try {
            //创建持久节点
           if (client.checkExists().forPath("/bgm") == null) {
               client.create().creatingParentsIfNeeded()
                     .withMode(CreateMode.PERSISTENT)
                     .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                     .forPath("/bgm");
               log.info("zk初始化成功!");
               log.info("zk服务器状态:{}", client.isStarted());
           }

        } catch (Exception e) {
            log.error("zk初始化失败!");
            e.printStackTrace();
        }
    }
    
    /**
     * @Desciption:增加或删除BGM,并创建ZKNode,给小程序后端监听
     * @version:v-1.00
     * @return:
     * @author:张琪灵
     */
    public void sendBgnOperator(String bgmId, String opMap) {
        try {
            //创建持子节点
            client.create().creatingParentsIfNeeded()
                     .withMode(CreateMode.PERSISTENT)
                     .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                     .forPath("/bgm/" + bgmId , opMap.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
