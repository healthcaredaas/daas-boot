package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.web.core.utils.HeaderSecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**

 * @ClassName： BaseWriteController.java
 * @Author： chenpan
 * @Date：2024/12/12 12:07
 * @Modify：
 */
public interface BaseWriteController<E extends BaseEntity, V extends BaseEntity> extends BaseController<E, V> {

    default RestResult<String> add(@RequestBody @Validated V v) {
        v.setCreateBy(HeaderSecurityUtils.getUserId());
        E entity = vo2Entity(v);
        getService().add(entity);

        return result(entity.getId());
    }

    default RestResult<String> update(String id, V v) {
        if (v.getId() == null) {
            v.setId(id);
        }
        v.setUpdateBy(HeaderSecurityUtils.getUserId());
        getService().updateNotNull(vo2Entity(v));
        return RestResult.success("更新成功！", id);
    }

    default RestResult<String> delete(String id) {
        getService().remove(id);
        return RestResult.success("删除成功！", id);
    }


    default RestResult<List<String>> deleteBatch(List<String> ids) {
        getService().batchRemove(ids);
        return RestResult.success("批量删除成功！", ids);
    }
}
