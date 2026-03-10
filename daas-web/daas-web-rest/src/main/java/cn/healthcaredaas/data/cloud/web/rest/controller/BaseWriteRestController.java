package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>基础写控制器</pre>
 *
 * @ClassName： BaseWriteRestController.java
 * @Author： chenpan
 * @Date：2024/12/12 12:07
 * @Modify：
 */
public abstract class BaseWriteRestController<E extends BaseEntity, V extends BaseEntity> extends BaseRestController<E, V>
        implements BaseWriteController<E, V> {

    @PostMapping()
    @Operation(summary = "新增")
    public RestResult<String> add(@RequestBody @Validated V v) {
        return BaseWriteController.super.add(v);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新")
    public RestResult<String> update(@PathVariable("id") String id, @RequestBody @Validated V v) {
        return BaseWriteController.super.update(id, v);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public RestResult<String> delete(@PathVariable("id") String id) {
        return BaseWriteController.super.delete(id);
    }


    @DeleteMapping("/batch")
    @Operation(summary = "批量删除")
    public RestResult<List<String>> deleteBatch(@RequestParam("ids")List<String> ids) {
        return BaseWriteController.super.deleteBatch(ids);
    }
}
