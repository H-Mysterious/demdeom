package com.example.demo.domain.znode.service;


import com.example.demo.domain.znode.entity.Znode;
import org.apache.zookeeper.data.Stat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * zk节点的stat对象与领域对象转换工厂
 *
 * @author : HanMinyang
 * @date : 2020-10-19 11:12
 **/

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ZnodeFactory extends BaseEntityFactory<Stat, Znode> {

    @Override
    @Mapping(target = "ctime", expression = "java(new java.util.Date(stat.getCtime()))")
    @Mapping(target = "mtime", expression = "java(new java.util.Date(stat.getMtime()))")
    Znode toPo(Stat stat);

    @Override
    @Mapping(target = "ctime", expression = "java(po.getCtime() == null ? 0 : po.getCtime().getTime())")
    @Mapping(target = "mtime", expression = "java(po.getMtime() == null ? 0 : po.getMtime().getTime())")
    Stat toEntity(Znode po);
}