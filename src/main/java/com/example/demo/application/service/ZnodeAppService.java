package com.example.demo.application.service;


import com.example.demo.domain.znode.entity.Znode;
import com.example.demo.domain.znode.service.ZkNodeDomainService;
import com.example.demo.infrastructure.util.ZkClientUtil;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HanMinyang
 * @date 2020/10/16
 **/
@Service
@RequiredArgsConstructor
public class ZnodeAppService {

    private final ZkNodeDomainService zkNodeDomainService;

    /**
     * 新增zk节点
     *
     * @param znode zk节点
     * @param type  zk节点类型
     * @return 返回成功信息
     */
    public String addNode(Znode znode, CreateMode type) {
        zkNodeDomainService.addNode(znode, type);
        return "新增ZK节点成功！";
    }

    /**
     * 修改zk节点
     *
     * @param znode zk节点
     * @return 返回成功信息
     */
    public String updateNode(Znode znode) {
        zkNodeDomainService.updateNode(znode);
        return "修改ZK节点成功！";
    }

    /**
     * 删除zk节点
     *
     * @param zkAddress zk节点
     * @param path      路径
     * @return 返回成功信息
     */
    public String deleteNode(String zkAddress, String path) {
        zkNodeDomainService.deleteNode(zkAddress, path);
        return "删除ZK节点成功！";
    }

    /**
     * 获取所有子节点的信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return Zk节点信息
     */
    public Znode getAllZkTreeNodes(String zkAddress, String path) {
        return zkNodeDomainService.getAllSubNodes(zkAddress, path);
    }

    /**
     * 获取下一层子节点信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return Zk节点信息
     */
    public List<Znode> getSubNodes(String zkAddress, String path) {
        return zkNodeDomainService.getSubNodes(zkAddress, path);
    }

    /**
     * 初始化新增的zk连接
     *
     * @param clusterId        zk地址
     * @param curatorFramework zk连接
     */
    public void addCluster(String clusterId, CuratorFramework curatorFramework) {
        ZkClientUtil.init(clusterId, curatorFramework);
    }

    /**
     * 剔除zk连接
     *
     * @param clusterId zk地址
     */
    public void removeCluster(String clusterId) {
        ZkClientUtil.close(clusterId);
    }
}