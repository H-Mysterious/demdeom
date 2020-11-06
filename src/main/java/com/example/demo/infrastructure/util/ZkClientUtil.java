package com.example.demo.infrastructure.util;


import com.example.demo.infrastructure.exception.ZookeeperException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author HanMinyang
 * @date 2020-10-13
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ZkClientUtil {

    private static final ConcurrentMap<String, CuratorFramework> CLIENTS = new ConcurrentHashMap<>();

    /**
     * 初始化zk客户端
     *
     * @param clusterId        zk客户端id
     * @param curatorFramework zk客服端连接
     */
    public static void init(String clusterId, CuratorFramework curatorFramework) {
        checkClusterId(clusterId);
        CLIENTS.putIfAbsent(clusterId, curatorFramework);
    }

    /**
     * 关闭zk客户端
     *
     * @param clusterId zk客户端id
     */
    public static void close(String clusterId) {
        checkClusterId(clusterId);
        CuratorFramework client = CLIENTS.get(clusterId);
        if (client != null) {
            CLIENTS.remove(clusterId);
        }
    }

    /**
     * 获取节点stat信息
     *
     * @param path      路径
     * @param clusterId zk客户端id
     * @return zk节点stat信息
     * @throws Exception 异常
     */
    public static Stat checkNodes(String path, String clusterId) throws Exception {
        checkClusterId(clusterId);
        CuratorFramework client = getClient(clusterId);
        return client.checkExists().forPath(path);
    }

    /**
     * 获取路径下的子树名称
     *
     * @param path      路径
     * @param clusterId zk客户端id
     * @return 路径的子树名称 （String)
     * @throws Exception 异常
     */
    public static List<String> getChildren(String path, String clusterId) throws Exception {
        checkClusterId(clusterId);
        CuratorFramework client = getClient(clusterId);
        return client.getChildren().forPath(path);
    }

    /**
     * 获取节点的数据
     *
     * @param path      路径
     * @param clusterId zk客户端id
     * @return 节点数据
     * @throws Exception 异常
     */
    public static String getData(String path, String clusterId) throws Exception {
        checkClusterId(clusterId);
        CuratorFramework client = getClient(clusterId);
        byte[] bytes = client.getData().forPath(path);
        return ArrayUtils.isEmpty(bytes) ? "" : new String(bytes);
    }

    /**
     * 删除节点
     *
     * @param path      路径
     * @param clusterId zk客户端id
     * @throws Exception 异常
     */
    public static void delNode(String path, String clusterId) throws Exception {
        checkClusterId(clusterId);
        CuratorFramework client = getClient(clusterId);
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    /**
     * 更新节点数据
     *
     * @param path      路径
     * @param data      数据
     * @param clusterId zk客户端id
     * @throws Exception 异常
     */
    public static void setData(String path, String data, String clusterId) throws Exception {
        checkClusterId(clusterId);
        CuratorFramework client = getClient(clusterId);
        client.setData().forPath(path, data.getBytes());

    }

    /**
     * 增加节点
     *
     * @param path       路径
     * @param data       数据
     * @param createMode 节点类型：持久、临时、持久顺序、临时顺序
     * @param clusterId  zk客户端id
     * @throws Exception 异常
     */
    public static void addNodes(String path, String data, CreateMode createMode, String clusterId) throws Exception {
        checkClusterId(clusterId);
        CuratorFramework client = getClient(clusterId);
        client.create().creatingParentContainersIfNeeded().withMode(createMode).forPath(path, data.getBytes());
    }


    /**
     * 获取zk客户端连接
     *
     * @param clusterId zk客户端id
     * @return zk客户端连接
     */
    private static CuratorFramework getClient(String clusterId) {
        CuratorFramework client = CLIENTS.get(clusterId);
        if (client == null) {
            throw new ZookeeperException("zookeeper地址:" + clusterId + "错误, 无该地址信息！");
        }
        return client;
    }

    /**
     * 校验zk客户端id
     *
     * @param clusterId zk客户端id
     */
    private static void checkClusterId(String clusterId) {
        if (StringUtils.isBlank(clusterId)) {
            throw new ZookeeperException("zookeeper地址不能为空！");
        }
    }
}