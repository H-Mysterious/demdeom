package com.example.demo.interfaces.assembler;

import com.github.pagehelper.PageInfo;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO与Entity的基类转换器
 *
 * @author Ernest.Wu
 * @date 2020/9/27
 */
public interface BaseAssembler<D, E> {

    /**
     * DTO转Entity
     *
     * @param dto 数据传输载体实例
     * @return 实体类
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     *
     * @param entity 实体类
     * @return 数据传输载体实例
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoList 数据传输载体实例集合
     * @return 实体对象集合
     */
    default List<E> toEntity(List<D> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    /**
     * Entity集合转DTO集合
     *
     * @param entityList 实体类集合
     * @return 数据传输载体实例集合
     */
    default List<D> toDto(List<E> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Entity分页结果集合转DTO集合
     *
     * @param entityPageInfo entity分页结果集合
     * @return DTO分页结果集合
     */
    default PageInfo<D> toDto(PageInfo<E> entityPageInfo) {
        if (entityPageInfo == null || CollectionUtils.isEmpty(entityPageInfo.getList())) {
            return new PageInfo<>(Collections.emptyList());
        }
        PageInfo<D> dtoPageInfo = new PageInfo<>(toDto(entityPageInfo.getList()));
        dtoPageInfo.setPageNum(entityPageInfo.getPageNum());
        dtoPageInfo.setPageSize(entityPageInfo.getPageSize());
        dtoPageInfo.setTotal(entityPageInfo.getTotal());
        return dtoPageInfo;
    }
}