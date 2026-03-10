package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.healthcaredaas.data.cloud.audit.annotation.OperationLog;
import cn.healthcaredaas.data.cloud.audit.enums.OperationType;
import cn.healthcaredaas.data.cloud.core.entity.AbstractEntity;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseTreeEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <pre>基础树形CRUD 控制器</pre>
 *
 * @ClassName： BaseTreeCRUDController.java
 * @Author： chenpan
 * @Date：2024/11/30 15:55
 * @Modify：
 */
public abstract class BaseTreeCRUDController<E extends BaseTreeEntity<E>, V extends BaseTreeEntity<E>, Q extends AbstractEntity> extends BaseRestController<E, V>
        implements BaseReadController<E, V, Q>, BaseWriteController<E, V> {

    @Operation(summary = "查询记录数")
    @GetMapping("/count")
    public RestResult<Long> count(Q q) throws IllegalAccessException {
        return BaseReadController.super.count(q);
    }

    @GetMapping("/one")
    @Operation(summary = "根据主键查询数据")
    public RestResult<V> getById(@RequestParam String id) {
        return BaseReadController.super.getById(id);
    }

    @GetMapping()
    @Operation(summary = "分页查询")
    public RestResult<Map<String, Object>> pageList(Q q,
                                                    @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) throws IllegalAccessException {
        return BaseReadController.super.pageList(q, pageNo, pageSize);
    }

    @GetMapping("/all")
    @Operation(summary = "查询全部")
    public RestResult<List<V>> list(Q q) throws IllegalAccessException {
        return BaseReadController.super.list(q);
    }

    @GetMapping("/tree")
    @Operation(summary = "查询树形结构")
    public RestResult<List<V>> tree(Q q) throws IllegalAccessException {
        List<E> list = getService().getByWrapper(getCrudWrapper().baseQueryWrapper(query2Entity(q)));
        return result(list.stream()
                .map(this::entity2Vo)
                .collect(java.util.stream.Collectors.toList())
        );
    }

    @PostMapping()
    @Operation(summary = "新增")
    @OperationLog(type = OperationType.CREATE, desc = "新增", businessId = "#result.data")
    public RestResult<String> add(@RequestBody @Validated V v) {
        return BaseWriteController.super.add(v);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新")
    @OperationLog(type = OperationType.UPDATE, desc = "更新", businessId = "#id")
    public RestResult<String> update(@PathVariable("id") String id, @RequestBody @Validated V v) {
        return BaseWriteController.super.update(id, v);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    @OperationLog(type = OperationType.DELETE, desc = "删除", businessId = "#id")
    public RestResult<String> delete(@PathVariable("id") String id) {
        return BaseWriteController.super.delete(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除")
    @OperationLog(type = OperationType.DELETE, desc = "批量删除", businessId = "#ids")
    public RestResult<List<String>> deleteBatch(@RequestParam("ids") List<String> ids) {
        return BaseWriteController.super.deleteBatch(ids);
    }
}