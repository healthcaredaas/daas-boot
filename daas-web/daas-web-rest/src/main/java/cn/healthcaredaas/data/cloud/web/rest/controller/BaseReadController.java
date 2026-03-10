package cn.healthcaredaas.data.cloud.web.rest.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.healthcaredaas.data.cloud.core.entity.AbstractEntity;
import cn.healthcaredaas.data.cloud.core.exception.PlatformException;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>基础读控制器</pre>
 *
 * @ClassName： BaseReadController.java
 * @Author： chenpan
 * @Date：2024/12/12 12:08
 * @Modify：
 */
public interface BaseReadController<E extends BaseEntity, V extends BaseEntity, Q extends AbstractEntity> extends BaseController<E, V> {

    /**
     * 是否转换结果对象
     *
     * @return
     */
    default boolean isConvertEntity() {
        return false;
    }

    /**
     * 查询参数类转实体类
     *
     * @param q
     * @return
     */
    default E query2Entity(Q q) {
        Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (q.getClass().isAssignableFrom(clazz)) {
            return (E) q;
        }
        try {
            E newInstance = clazz.getDeclaredConstructor().newInstance();
            BeanUtil.copyProperties(q, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new PlatformException(e);
        }
    }

    /**
     * 查询记录数
     *
     * @param q
     * @return
     * @throws IllegalAccessException
     */
    default RestResult<Long> count(Q q) throws IllegalAccessException {
        Long count = getService().count(getCrudWrapper().baseQueryWrapper(query2Entity(q)));
        return RestResult.success("成功！", count);
    }

    /**
     * 根据主键查询数据
     *
     * @param id
     * @return
     */
    default RestResult<V> getById(@RequestParam String id) {
        E entity = getService().getById(id);
        return result(entity2Vo(entity));
    }

    /**
     * 分页查询
     *
     * @param q
     * @param pageNo
     * @param pageSize
     * @return
     * @throws IllegalAccessException
     */
    default RestResult<Map<String, Object>> pageList(Q q, Integer pageNo, Integer pageSize) throws IllegalAccessException {

        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;

        Page<E> page = new Page<>(pageNo, pageSize);
        IPage<E> pageInfo = getService().pageList(page, getCrudWrapper().baseQueryWrapper(query2Entity(q)));
        if (!isConvertEntity()) {
            return result(pageInfo);
        }

        IPage<V> vPage = new Page<>(pageNo, pageSize, pageInfo.getTotal());
        vPage.setRecords(pageInfo.getRecords().stream()
                .map(this::entity2Vo)
                .collect(Collectors.toList())
        );
        return result(vPage);
    }

    /**
     * 查询所有列表
     *
     * @param q
     * @return
     * @throws IllegalAccessException
     */
    default RestResult<List<V>> list(Q q) throws IllegalAccessException {
        List<E> rs = getService().getByWrapper(getCrudWrapper().baseQueryWrapper(query2Entity(q)));
        return result(rs.stream()
                .map(this::entity2Vo)
                .collect(Collectors.toList())
        );
    }
}
