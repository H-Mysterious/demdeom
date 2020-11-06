package com.example.demo.interfaces.assembler;

import com.example.demo.domain.znode.entity.Znode;
import com.example.demo.interfaces.dto.ZnodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Ernest.Wu
 * @date 2020/10/19
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ZnodeAssembler extends BaseAssembler<ZnodeDto, Znode> {
}