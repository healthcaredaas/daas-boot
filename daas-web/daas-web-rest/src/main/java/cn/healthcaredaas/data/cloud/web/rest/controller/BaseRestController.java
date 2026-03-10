package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.mybatisplus.service.IBaseService;
import cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper.BaseCRUDWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

/**

 * @ClassName： BaseRestController.java
 * @Author： chenpan
 * @Date：2024/12/12 13:48
 * @Modify：
 */
public abstract class BaseRestController<E extends BaseEntity, V extends BaseEntity>
        implements BaseController<E, V> {

    @Autowired
    private IBaseService<E> baseService;

    @Setter
    private BaseCRUDWrapper<E> baseCRUDWrapper;

    public BaseRestController() {
        this.baseCRUDWrapper = new BaseCRUDWrapper<E>();
    }

    @Override
    public IBaseService<E> getService() {
        return this.baseService;
    }

    @Override
    public BaseCRUDWrapper<E> getCrudWrapper() {
        return this.baseCRUDWrapper;
    }

    @Override
    public E vo2Entity(V v) {
        Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (v.getClass().isAssignableFrom(clazz)) {
            return (E) v;
        }
        return convert(v, clazz);
    }

    @Override
    public V entity2Vo(E e) {
        Class<V> vClazz = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        if (e.getClass().isAssignableFrom(vClazz)) {
            return (V) e;
        }
        return convertEntity(e, vClazz);
    }

    private V convertEntity(E e, Class<V> clazz) {
        try {
            V newInstance = clazz.getDeclaredConstructor().newInstance();
            BeanUtil.copyProperties(e, newInstance);
            return newInstance;
        } catch (Exception ex) {
            throw new PlatformException(ex);
        }
    }

    private E convert(V v, Class<E> clazz) {
        try {
            E newInstance = clazz.getDeclaredConstructor().newInstance();
            BeanUtil.copyProperties(v, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new PlatformException(e);
        }
    }
}
