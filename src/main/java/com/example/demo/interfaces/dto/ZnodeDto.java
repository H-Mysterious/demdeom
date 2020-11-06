package com.example.demo.interfaces.dto;

import com.example.demo.domain.znode.entity.Znode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author HanMinyang
 * @date 2020-10-16
 **/

@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"clusterId", "absolutePath"})
@ApiModel("zk节点Dto")
public class ZnodeDto {

    /**
     * 集群标识
     */
    @ApiModelProperty(value =  "集群标识")
    @NotNull
    private String clusterId;

    /**
     * 绝对路径
     */
    @ApiModelProperty(value =  "绝对路径")
    @NotNull
    private String absolutePath;

    /**
     * 节点名称
     */
    @ApiModelProperty(value =  "节点名称")
    private String label;

    /**
     * 子节点
     */
    @ApiModelProperty(value =  "子节点")
    private List<Znode> children;

    /**
     * 节点数据
     */
    @ApiModelProperty(value =  "节点数据")
    private String data;

    /**
     * 创建节点的事务ID
     */
    @ApiModelProperty(value =  "创建节点的事务ID")
    private Long czxid;

    /**
     * 最后修改节点的事务ID
     */
    @ApiModelProperty(value =  "最后修改节点的事务ID")
    private Long mzxid;

    /**
     * 表示从1970-01-01T00:00:00Z开始的节点创建时间
     */
    @ApiModelProperty(value =  "节点创建时间")
    private Date ctime;

    /**
     * 表示从1970-01-01T00:00:00Z开始的节点最近修改时间
     */
    @ApiModelProperty(value =  "节点最近修改时间")
    private Date mtime;

    /**
     * 表示对该节点的数据所做的更改次数
     */
    @ApiModelProperty(value =  "对该节点的数据所做的更改次数")
    private Integer version;

    /**
     * 表示对该子节点的数据所做的更改次数
     */
    @ApiModelProperty(value =  "对该子节点的数据所做的更改次数")
    private Integer cversion;

    /**
     * 表示对该节点的ACL所做的更改次数
     */
    @ApiModelProperty(value =  "对该节点的ACL所做的更改次数")
    private Integer aversion;

    /**
     * 如果节点是ephemeral类型节点，则这是节点所有者的session ID
     * 如果节点不是ephemeral类型节点，则该字段设置为零
     */
    @ApiModelProperty(value =  "节点所有者的session ID")
    private Long ephemeralOwner;

    /**
     * 表示节点的子节点的长度
     */
    @ApiModelProperty(value =  "节点的子节点的长度")
    private Integer dataLength;

    /**
     * 表示节点的子节点的数量
     */
    @ApiModelProperty(value =  "节点的子节点的数量")
    private Integer numChildren;

    /**
     * 用于添加或删除子节点的节点事务ID
     */
    @ApiModelProperty(value =  "用于添加或删除子节点的节点事务ID")
    private Long pzxid;

    /**
     * 节点类型
     */
    @ApiModelProperty(value =  "节点类型")
    private Integer type;

    public Integer getType() {
        if (ephemeralOwner != null && ephemeralOwner > 0) {
            // 持久节点
            return 0;
        }
        // 默认为持久节点
        return type == null ? 0 : type;
    }

    /**
     * 获取父路径
     *
     * @return 父路径
     */
    public String getParentNode() {
        int index = StringUtils.lastIndexOf(absolutePath, '/');
        return index > 0 ? absolutePath.substring(0, index) : "/";
    }

    /**
     * 是否为叶子节点
     *
     * @return 是否
     */
    public Boolean getLeaf() {
        return numChildren <= 0;
    }
}