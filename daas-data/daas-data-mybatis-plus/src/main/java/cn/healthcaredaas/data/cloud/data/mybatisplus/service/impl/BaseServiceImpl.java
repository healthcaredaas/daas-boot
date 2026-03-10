package cn.healthcaredaas.data.cloud.data.mybatisplus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.*;
import cn.hutool.extra.spring.SpringUtil;
import cn.healthcaredaas.data.cloud.core.exception.BizException;
import cn.healthcaredaas.data.cloud.core.utils.CheckUtils;
import cn.healthcaredaas.data.cloud.data.core.annotation.DeleteOption;
import cn.healthcaredaas.data.cloud.data.core.annotation.LogicUnique;
import cn.healthcaredaas.data.cloud.data.core.annotation.Relation;
import cn.healthcaredaas.data.cloud.data.core.annotation.RelationCondition;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import cn.healthcaredaas.data.cloud.data.mybatisplus.service.IBaseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**

 * @ClassName： BaseServiceImpl.java
 * @Author： chenpan
 * @Date：2024/11/29 12:03
 * @Modify：
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T>
        implements IBaseService<T> {

    protected Class<T> getClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public IPage<T> pageList(Page<T> page, Wrapper<T> wrapper) {
        return super.page(page, wrapper);
    }

    /**
     * 获取全部数据
     *
     * @return
     */
    @Override
    public List<T> getAll() {
        return super.list();
    }

    /**
     * 获取总数量
     *
     * @param entity
     * @return
     */
    @Override
    public long count(T entity) {
        return super.count(Wrappers.query(entity));
    }

    /**
     * 获取满足条件的数量
     *
     * @param wrapper
     * @return
     */
    @Override
    public long count(Wrapper<T> wrapper) {
        return super.count(wrapper);
    }

    /**
     * 根据主键Id获取数据
     *
     * @param id
     * @return
     */
    @Override
    public T getById(String id) {
        return super.getById(id);
    }

    @Override
    public T findOne(T entity) {
        return this.findOne(Wrappers.query(entity));
    }

    @Override
    public T findOne(Wrapper<T> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    public void checkLogicUnique(T entity, String updateId) {
        Class<T> clazz = getClazz();
        if (clazz.isAnnotationPresent(LogicUnique.class)) {
            LogicUnique[] logicUniques = clazz.getAnnotationsByType(LogicUnique.class);
            if (ArrayUtil.isNotEmpty(logicUniques)) {
                for (LogicUnique unique : logicUniques) {
                    String[] columns = unique.columns();
                    if (ArrayUtil.isNotEmpty(columns)) {
                        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
                        for (String column : columns) {
                            String columnName = ColumnUtil.field2ColumnName(clazz, column);
                            queryWrapper.eq(columnName, BeanUtil.getFieldValue(entity, column));
                        }
                        if (ObjectUtil.isNotNull(updateId)) {
                            queryWrapper.ne(BaseEntity.ID_COLUMN, updateId);
                        }
                        long count = super.count(queryWrapper);
                        if (count > 0) {
                            String errorMsg = StrUtil.format(unique.message(), BeanUtil.beanToMap(entity));
                            throw new BizException(errorMsg);
                        }
                    }
                }
            }
        }
    }

    /**
     * 保存对象
     *
     * @param entity
     * @return
     */
    @Override
    public boolean add(T entity) {
        this.checkLogicUnique(entity, null);
        return save(entity);
    }

    /**
     * 根据主键id删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(String id) {
        Class<T> clazz = getClazz();
        T entity = getById(id);
        CheckUtils.notEmpty(entity, "删除的数据不存在！");
        if (clazz.isAnnotationPresent(DeleteOption.class)) {
            DeleteOption option = clazz.getAnnotation(DeleteOption.class);
            if (option.deleteRelation() && ArrayUtil.isNotEmpty(option.relations())) {
                for (Relation relation : option.relations()) {
                    if (ArrayUtil.isNotEmpty(relation.conditions())) {
                        Class<?> relationClass = relation.entity();
                        BaseMapper relationMapper = (BaseMapper) SpringUtil.getBean(relation.relationMapper());
                        QueryWrapper qw = new QueryWrapper<>();
                        for (RelationCondition condition : relation.conditions()) {
                            qw.eq(ColumnUtil.field2ColumnName(relationClass, condition.relationField()), BeanUtil.getFieldValue(entity, condition.field()));
                        }
                        relationMapper.delete(qw);
                    }
                }
            }
        }
        return super.removeById(id);
    }

    /**
     * 根据主键批量删除
     *
     * @param list
     * @return
     */
    @Override
    public boolean batchRemove(List<String> list) {
        return super.removeByIds(list);
    }

    /**
     * 根据主键更新所有字段
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateAll(T entity) {
        this.checkLogicUnique(entity, entity.getId());
        return super.updateById(entity);
    }

    /**
     * 根据主键更新所有不为空字段
     *
     * @param entity
     * @return
     */
    @Override
    public boolean updateNotNull(T entity) {
        this.checkLogicUnique(entity, entity.getId());
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(BaseEntity.ID_COLUMN, entity.getId());
        return super.update(entity, updateWrapper);
    }

    /**
     * 条件查询
     *
     * @param wrapper
     * @return
     */
    @Override
    public List<T> getByWrapper(Wrapper<T> wrapper) {
        return super.list(wrapper);
    }

    /**
     * 根据ID串批量查询
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> getByIds(List<String> ids) {
        return super.listByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recursiveDelete(String id) {
        List<String> idList = new ArrayList<>();
        recursiveSelectChild(id, idList);
        idList.add(id);
        idList.forEach(this::remove);
    }

    private void recursiveSelectChild(String id, List<String> idList) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        wrapper.select(BaseEntity.ID_COLUMN);
        List<T> childIdList = baseMapper.selectList(wrapper);
        childIdList.forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.recursiveSelectChild(item.getId(), idList);
        });
    }
}
