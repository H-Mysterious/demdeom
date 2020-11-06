package com.example.demo.domain.znode.service;


import com.example.demo.domain.znode.entity.Znode;
import com.example.demo.infrastructure.exception.ZookeeperException;
import com.example.demo.infrastructure.util.ZkClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 注册信息管理的领域服务
 *
 * @author HanMinyang
 * @date 2020/10/13
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class ZkNodeDomainService {
    private static final String PATH_SEPARATOR = "/";

    private final ZnodeFactory znodeFactory;

    /**
     * 获取所有子节点Znode信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return Zk节点信息
     */
    public Znode getAllSubNodes(String zkAddress, String path) {
        if (StringUtils.isEmpty(path)) {
            path = PATH_SEPARATOR;
        }
        Znode znode = getZnode(zkAddress, path);
        znode.setChildren(getChildrenNodes(zkAddress, path, true));
        return znode;
    }

    /**
     * 获取子节点（仅下一层）Znode信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return Zk节点信息
     */
    public List<Znode> getSubNodes(String zkAddress, String path) {
        if (StringUtils.isEmpty(path)) {
            path = PATH_SEPARATOR;
        }
        return getChildrenNodes(zkAddress, path, false);
    }

    /**
     * 获取子节点的Znode信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @param isAll     是否是所有子节点
     * @return Zk节点信息List
     */
    private List<Znode> getChildrenNodes(String zkAddress, String path, boolean isAll) {
        List<String> children = getChildren(zkAddress, path);
        List<Znode> znodes = new ArrayList<>();
        if (PATH_SEPARATOR.equals(path)) {
            path = "";
        }
        for (String child : children) {
            String childPath = path + PATH_SEPARATOR + child;
            Znode znode = getZnode(zkAddress, childPath);
            List<Znode> grandChildrenNodes = new ArrayList<>();
            if (isAll) {
                grandChildrenNodes = getChildrenNodes(zkAddress, childPath, true);
            }
            znode.setChildren(grandChildrenNodes);
            znodes.add(znode);
        }
        return znodes;
    }

    /**
     * 增加节点
     *
     * @param znode 节点信息
     * @param type  节点类型
     */
    public void addNode(Znode znode, CreateMode type) {
        try {
            ZkClientUtil.addNodes(znode.getAbsolutePath(), znode.getData(), type, znode.getClusterId());
        } catch (Exception e) {
            throw new ZookeeperException(e, "add zkNode error!");
        }
    }

    /**
     * 更新节点
     *
     * @param znode 节点信息
     */
    public void updateNode(Znode znode) {
        try {
            ZkClientUtil.setData(znode.getAbsolutePath(), znode.getData(), znode.getClusterId());
        } catch (Exception e) {
            throw new ZookeeperException(e, "update zkNode error!");
        }
    }

    /**
     * 删除节点
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     */
    public void deleteNode(String zkAddress, String path) {
        try {
            ZkClientUtil.delNode(path, zkAddress);
        } catch (Exception e) {
            throw new ZookeeperException(e, "delete zkNode error!");
        }
    }

    /**
     * 无子树的信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return Zk节点信息
     */
    private Znode getZnode(String zkAddress, String path) {
        Stat stat = getZnodeStat(zkAddress, path);
        Znode znode = znodeFactory.toPo(stat);
        znode.setAbsolutePath(path);
        znode.setLabel(path.substring(path.lastIndexOf(PATH_SEPARATOR) + 1));
        znode.setData(getData(zkAddress, path));
        znode.setClusterId(zkAddress);
        return znode;
    }

    /**
     * 获取节点Stat信息
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return Zk节点Stat信息
     */
    public Stat getZnodeStat(String zkAddress, String path) {
        Stat stat;
        try {
            stat = ZkClientUtil.checkNodes(StringUtils.isBlank(path) ? PATH_SEPARATOR : path, zkAddress);
        } catch (Exception e) {
            throw new ZookeeperException(e, "get zkNode stat error!");
        }
        return stat;
    }

    /**
     * 获取节点的数据
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return 节点数据
     */
    private String getData(String zkAddress, String path) {
        try {
            return ZkClientUtil.getData(path, zkAddress);
        } catch (Exception e) {
            throw new ZookeeperException(e, "get zkNode data error!");
        }
    }

    /**
     * 获取path下的子树
     *
     * @param zkAddress 注册中心地址
     * @param path      路径
     * @return String的List集合
     */
    private List<String> getChildren(String zkAddress, String path) {
        try {
            return ZkClientUtil.getChildren(path, zkAddress);
        } catch (Exception e) {
            throw new ZookeeperException(e, "get zkNode children error!");
        }
    }
}