package cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.core.annotation.SelectLikeColumn;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;

/**
 * @className: LikeColumnWrapper
 * @description: <p></p>
 * @author: chenpan
 * @date: 2021/11/20 15:59
 **/
public class LikeColumnWrapper {

    public static <T extends BaseEntity> void likeField(QueryWrapper<T> queryWrapper, T m, Field field) throws IllegalAccessException {
        SelectLikeColumn selectLikeColumn = field.getAnnotation(SelectLikeColumn.class);
        if (selectLikeColumn != null) {
            if (selectLikeColumn.value()) {
                field.setAccessible(true);
                Object fieldValue = field.get(m);
                boolean notEmpty = fieldValue != null
                        && StrUtil.isNotBlank((String) fieldValue);
                if (notEmpty) {
                    switch (selectLikeColumn.wildcardPosition()) {
                        case BOTH:
                            queryWrapper.like(ColumnUtil.field2ColumnName(field), fieldValue);
                            BeanUtil.setFieldValue(m, field.getName(), null);
                            break;
                        case AFTER:
                            queryWrapper.likeRight(ColumnUtil.field2ColumnName(field), fieldValue);
                            BeanUtil.setFieldValue(m, field.getName(), null);
                            break;
                        case BEFORE:
                            queryWrapper.likeLeft(ColumnUtil.field2ColumnName(field), fieldValue);
                            BeanUtil.setFieldValue(m, field.getName(), null);
                            break;
                        default:
                            break;
                    }
                }
            }
            BeanUtil.setFieldValue(m, field.getName(), null);
        }
    }
}
