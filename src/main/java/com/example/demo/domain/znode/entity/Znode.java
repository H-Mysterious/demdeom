package com.example.demo.domain.znode.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;


/**
 * ZooKeeper节点信息实体(聚合根)
 *
 * @author HanMinyang
 * @date 2020/10/13
 **/
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"clusterId", "absolutePath"})
public class Znode {

    /**
     * 集群标识
     */
    private String clusterId;

    /**
     * 绝对路径
     */
    private String absolutePath;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    private List<Znode> children;

    /**
     * 节点数据
     */
    private String data;

    /**
     * 创建节点的事务ID
     */
    private Long czxid;

    /**
     * 最后修改节点的事务ID
     */
    private Long mzxid;

    /**
     * 表示从1970-01-01T00:00:00Z开始的节点创建时间
     */
    private Date ctime;

    /**
     * 表示从1970-01-01T00:00:00Z开始的节点最近修改时间
     */
    private Date mtime;

    /**
     * 表示对该节点的数据所做的更改次数
     */
    private Integer version;

    /**
     * 表示对该子节点的数据所做的更改次数
     */
    private Integer cversion;

    /**
     * 表示对该节点的ACL所做的更改次数
     */
    private Integer aversion;

    /**
     * 如果节点是ephemeral类型节点，则这是节点所有者的session ID
     * 如果节点不是ephemeral类型节点，则该字段设置为零
     */
    private Long ephemeralOwner;

    /**
     * 表示节点的子节点的长度
     */
    private Integer dataLength;

    /**
     * 表示节点的子节点的数量
     */
    private Integer numChildren;

    /**
     * 用于添加或删除子节点的节点事务ID
     */
    private Long pzxid;

    public String getData() {
        // 将null转为空串
        return StringUtils.trimToEmpty(data);
    }
}