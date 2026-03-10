package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.healthcaredaas.data.cloud.core.entity.AbstractEntity;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**

 * @ClassName： BaseReadRestController.java
 * @Author： chenpan
 * @Date：2024/12/12 12:08
 * @Modify：
 */
public abstract class BaseReadRestController<E extends BaseEntity, V extends BaseEntity, Q extends AbstractEntity> extends BaseRestController<E, V>
        implements BaseReadController<E, V, Q> {

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
                                                    @RequestParam(name = "pageNo", required = false, defaultValue = "1")
                                                    Integer pageNo, @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                                    Integer pageSize) throws IllegalAccessException {
        return BaseReadController.super.pageList(q, pageNo, pageSize);
    }

    @GetMapping("/all")
    @Operation(summary = "查询全部")
    public RestResult<List<V>> list(Q q) throws IllegalAccessException {
        return BaseReadController.super.list(q);
    }
}
