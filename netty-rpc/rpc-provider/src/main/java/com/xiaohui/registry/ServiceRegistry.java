package com.xiaohui.registry;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaohui
 * @Description: zookeeper管理类
 * @Date: 2019/6/2 8:20
 */
@Component
public class ServiceRegistry {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${registry.address}")
    private String registryAddress;

    private static final String ZK_REGISTRY_PATH = "/rpc";

    public void register(String data) {
        if (data != null) {
            ZkClient client = connectServer();
            if (client != null) {
                createNode(client, data);
            }
        }
    }
    private ZkClient connectServer() {
        ZkClient client = new ZkClient(registryAddress,20000,20000);
        client.setZkSerializer(new MyZkSerializer());
        return client;
    }

    /**
     * 创建zookeeper节点
     * 注意节点要设置临时有序的
     * @param client
     * @param data
     */
    private void createNode(ZkClient client, String data) {
        if(!client.exists(ZK_REGISTRY_PATH)){
            client.createPersistent(ZK_REGISTRY_PATH);
            logger.info("创建zookeeper主节点 {}",ZK_REGISTRY_PATH);
        }
        String path = client.create(ZK_REGISTRY_PATH + "/provider", data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        logger.info("创建zookeeper数据节点 ({} => {})", path, data);
    }
}
