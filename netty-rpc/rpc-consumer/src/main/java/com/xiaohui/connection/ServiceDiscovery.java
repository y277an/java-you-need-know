package com.xiaohui.connection;

import com.alibaba.fastjson.JSONObject;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: xiaohui
 * @Description: zookeeper管理类
 * @Date: 2019/6/1 18:35
 */
@Component
public class ServiceDiscovery {

    @Value("${registry.address}")
    private String registryAddress;

    @Autowired
    ConnectManage connectManage;

    // 服务地址列表
    private volatile List<String> addressList = new ArrayList<>();
    private static final String ZK_REGISTRY_PATH = "/rpc";
    private ZkClient client;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init(){
        client = connectServer();
        if (client != null) {
            watchNode(client);
        }
    }

    private ZkClient connectServer() {
        ZkClient client = new ZkClient(registryAddress,20000,20000);
        client.setZkSerializer(new MyZkSerializer());
        return client;
    }

    /**
     * 监听提供者的节点变化
     * @param client
     */
    private void watchNode(final ZkClient client) {
        List<String> nodes = client.subscribeChildChanges(ZK_REGISTRY_PATH, (s, nodeList) -> {
            logger.info("监听到子节点数据变化{}",JSONObject.toJSONString(nodeList));
            updateConnectedServer(nodeList);
        });
        logger.info("已发现服务列表...{}", JSONObject.toJSONString(addressList));
        updateConnectedServer(nodes);
    }

    /**
     * 更新消费端长连接
     * @param nodes
     */
    private void updateConnectedServer(List<String> nodes){
        addressList.clear();
        logger.info("/rpc子节点数据为:{}", JSONObject.toJSONString(nodes));
        for(String node:nodes){
            String address = client.readData(ZK_REGISTRY_PATH+"/"+node);
            addressList.add(address);
        }
        connectManage.updateConnectServer(addressList);
    }
}
