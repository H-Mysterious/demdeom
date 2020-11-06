package com.example.demo.domain.znode.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 持久化对象与领域对象互转的工厂接口
 *
 * @author Ernest.Wu
 * @date 2020/9/29
 */
public interface BaseEntityFactory<E, P> {

    /**
     * 持久化对象转领域对象
     *
     * @param po 持久化对象
     * @return 领域对象
     */
    E toEntity(P po);

    /**
     * 领域对象转持久化对象
     *
     * @param entity 领域对象
     * @return 持久化对象
     */
    P toPo(E entity);

    /**
     * 持久化对象集合转领域对象集合
     *
     * @param poList 持久化对象集合
     * @return 领域对象集合
     */
    default List<E> toEntity(List<P> poList) {
        return poList == null ? null : poList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * 领域对象集合转持久化对象集合
     *
     * @param entityList 领域对象集合
     * @return 持久化对象集合
     */
    default List<P> toPo(List<E> entityList) {
        return entityList == null ? null : entityList.stream().map(this::toPo).collect(Collectors.toList());
    }

    /**
     * 分页的持久化对象转换为分页的实体对象
     *
     * @param pPageInfo 分页的持久化对象
     * @return 分页的实体对象
     */
    default PageInfo<E> toEntity(PageInfo<P> pPageInfo) {
        if (pPageInfo == null) {
            return null;
        }
        PageInfo<E> pageInfo = new PageInfo<>(toEntity(pPageInfo.getList()));
        pageInfo.setPageNum(pPageInfo.getPageNum());
        pageInfo.setPageSize(pPageInfo.getPageSize());
        pageInfo.setTotal(pPageInfo.getTotal());
        return pageInfo;
    }
}