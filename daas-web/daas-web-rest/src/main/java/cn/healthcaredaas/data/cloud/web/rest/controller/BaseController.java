package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.healthcaredaas.data.cloud.core.entity.AbstractEntity;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.mybatisplus.service.IBaseService;
import cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper.BaseCRUDWrapper;

/**

 * @ClassName： BaseController.java
 * @Author： chenpan
 * @Date：2024/12/12 13:07
 * @Modify：
 */
public interface BaseController<E extends BaseEntity, V extends AbstractEntity> extends Controller {

    IBaseService<E> getService();

    BaseCRUDWrapper<E> getCrudWrapper();

    E vo2Entity(V v);

    V entity2Vo(E e);
}
