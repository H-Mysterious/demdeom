package com.example.demo.interfaces.facade;

import com.example.demo.application.service.ZnodeAppService;
import com.example.demo.interfaces.assembler.ZnodeAssembler;


import com.example.demo.interfaces.dto.ZnodeDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 注册中心的注册信息管理REST接口
 *
 * @author HanMinyang
 * @date 2020/10/15
 **/
@RestController
@RequiredArgsConstructor
@Api("zookeeper节点接口")
@RequestMapping(value = "/api/registry/znode")
public class ZnodeApi {

    private final ZnodeAssembler znodeAssembler;

    private final ZnodeAppService znodeAppService;

    /**
     * 新增zk节点
     * @param znodeDto zk节点信息
     * @return 返回信息
     * @throws KeeperException zk节点类型转换异常
     */
    @ApiOperation("新增zk节点")
    @PostMapping
    public ResponseEntity<String> createZkNode(@RequestBody ZnodeDto znodeDto) throws KeeperException {
        CreateMode createMode = CreateMode.fromFlag(znodeDto.getType());
        return ResponseEntity.ok(znodeAppService.addNode(znodeAssembler.toEntity(znodeDto), createMode));
    }

    /**
     * 修改zk节点信息
     * @param znodeDto zk节点信息
     * @return 返回信息
     */
    @ApiOperation("修改zk节点数据")
    @PutMapping
    public ResponseEntity<String> updateZkNode(@RequestBody ZnodeDto znodeDto) {
        String result = znodeAppService.updateNode(znodeAssembler.toEntity(znodeDto));
        return ResponseEntity.ok(result);
    }

    /**
     * 删除zk节点
     * @param znodeDto zk节点信息
     * @return 返回信息
     */
    @ApiOperation("删除zk节点")
    @DeleteMapping
    public ResponseEntity<String> deleteZkNode(@RequestBody ZnodeDto znodeDto) {
        return ResponseEntity.ok(znodeAppService.deleteNode(znodeDto.getClusterId(), znodeDto.getAbsolutePath()));
    }

    /**
     * 查询zk子节点信息
     * @param clusterId zk地址
     * @param path 路径
     * @return zk子节点信息
     */
    @ApiOperation("查询zk子节点信息")
    @GetMapping
    public ResponseEntity<PageInfo<ZnodeDto>> getSubZkNode(@NotNull @RequestParam String clusterId, @NotNull @RequestParam String path) {
        List<ZnodeDto> znodeDtoList = znodeAssembler.toDto(znodeAppService.getSubNodes(clusterId, path));
        return ResponseEntity.ok(new PageInfo<>(Optional.ofNullable(znodeDtoList).orElse(Collections.emptyList())));
    }

    /**
     * 查询zk所有子节点信息
     * @param clusterId zk地址
     * @param path 路径
     * @return zk所有子节点信息
     */
    @ApiOperation("查询zk所有子节点信息")
    @GetMapping("/all")
    public ResponseEntity<ZnodeDto> getAllZkTreeNodes(@NotNull @RequestParam String clusterId, @NotNull @RequestParam String path) {
        return ResponseEntity.ok(znodeAssembler.toDto(znodeAppService.getAllZkTreeNodes(clusterId, path)));
    }
}