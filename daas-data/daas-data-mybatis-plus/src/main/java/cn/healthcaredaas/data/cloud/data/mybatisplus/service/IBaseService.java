package cn.healthcaredaas.data.cloud.data.mybatisplus.service;

import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**

 * @ClassName： IBaseService.java
 * @Author： chenpan
 * @Date：2024/11/29 12:00
 * @Modify：
 */
public interface IBaseService<T extends BaseEntity> {

    /**
     * 分页查询
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    IPage<T> pageList(Page<T> page, Wrapper<T> queryWrapper);

    /**
     * 获取全部数据
     *
     * @return
     */
    List<T> getAll();

    /**
     * 获取总数量
     *
     * @return
     */
    long count(T t);

    /**
     * 获取满足条件的数量
     *
     * @param queryWrapper
     * @return
     */
    long count(Wrapper<T> queryWrapper);

    /**
     * 根据主键Id获取数据
     *
     * @param id
     * @return
     */
    T getById(String id);

    T findOne(T entity);

    T findOne(Wrapper<T> queryWrapper);

    /**
     * 保存对象
     *
     * @param entity
     * @return
     */
    boolean add(T entity);

    /**
     * 根据主键id删除
     *
     * @param id
     * @return
     */
    boolean remove(String id);

    /**
     * 根据主键批量删除
     *
     * @param list
     * @return
     */
    boolean batchRemove(List<String> list);

    /**
     * 根据主键更新所有字段
     *
     * @param entity
     * @return
     */
    boolean updateAll(T entity);

    /**
     * 根据主键更新所有不为空字段
     *
     * @param entity
     * @return
     */
    boolean updateNotNull(T entity);

    /**
     * 条件查询
     *
     * @param queryWrapper
     * @return
     */
    List<T> getByWrapper(Wrapper<T> queryWrapper);

    /**
     * 根据ID串批量查询
     *
     * @param ids
     * @return
     */
    List<T> getByIds(List<String> ids);

    /**
     * 递归删除
     *
     * @param id
     */
    void recursiveDelete(String id);
}
