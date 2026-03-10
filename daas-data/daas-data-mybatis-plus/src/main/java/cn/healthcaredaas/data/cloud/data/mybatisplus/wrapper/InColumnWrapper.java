package cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.healthcaredaas.data.cloud.data.core.annotation.SelectInColumn;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @className: InColumnWrapper
 * @description: <p></p>
 * @author: chenpan
 * @date: 2021/11/20 16:13
 **/
public class InColumnWrapper {

    public static <T extends BaseEntity> void inField(QueryWrapper<T> queryWrapper, T m, Field field) throws IllegalAccessException {
        SelectInColumn selectInColumn = field.getAnnotation(SelectInColumn.class);
        if (selectInColumn != null) {
            if (selectInColumn.value()) {
                field.setAccessible(true);
                Object fieldValue = field.get(m);
                if (fieldValue != null) {
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        queryWrapper.in(ColumnUtil.field2ColumnName(m.getClass(), selectInColumn.column()), (Collection<?>) fieldValue);
                    }
                    if (field.getType().isArray()) {
                        queryWrapper.in(ColumnUtil.field2ColumnName(m.getClass(), selectInColumn.column()), (Object[]) fieldValue);
                    }
                }
            }
            BeanUtil.setFieldValue(m, field.getName(), null);
        }
    }
}
