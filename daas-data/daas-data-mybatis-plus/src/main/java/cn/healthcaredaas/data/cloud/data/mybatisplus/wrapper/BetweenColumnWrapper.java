package cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.core.annotation.SelectBetweenColumn;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;

/**
 * @className: BetweenColumnWrapper
 * @description: <p></p>
 * @author: chenpan
 * @date: 2021/11/20 16:06
 **/
public class BetweenColumnWrapper {

    public static <T extends BaseEntity> void betweenField(QueryWrapper<T> queryWrapper, T m, Field field) throws IllegalAccessException {
        SelectBetweenColumn selectBetweenColumn = field.getAnnotation(SelectBetweenColumn.class);
        if (selectBetweenColumn != null) {
            if (selectBetweenColumn.value()) {
                field.setAccessible(true);
                Object fieldValue = field.get(m);
                Object startValue;
                Object endValue;
                switch (selectBetweenColumn.position()) {
                    case START:
                        startValue = fieldValue;
                        if (StrUtil.isNotEmpty(selectBetweenColumn.startValueColumn())) {
                            startValue = BeanUtil.getFieldValue(m, selectBetweenColumn.startValueColumn());
                        }
                        if (startValue != null) {
                            queryWrapper.ge(ColumnUtil.field2ColumnName(field), startValue);
                            BeanUtil.setFieldValue(m, selectBetweenColumn.startValueColumn(), null);
                        }
                        break;
                    case END:
                        endValue = fieldValue;
                        if (StrUtil.isNotEmpty(selectBetweenColumn.endValueColumn())) {
                            endValue = BeanUtil.getFieldValue(m, selectBetweenColumn.endValueColumn());
                        }
                        if (endValue != null) {
                            queryWrapper.le(ColumnUtil.field2ColumnName(field), endValue);
                            BeanUtil.setFieldValue(m, selectBetweenColumn.endValueColumn(), null);
                        }
                        break;
                    case START_AND_END:
                        if (StrUtil.isNotEmpty(selectBetweenColumn.startValueColumn())) {
                            startValue = BeanUtil.getFieldValue(m, selectBetweenColumn.startValueColumn());
                            if (startValue != null) {
                                queryWrapper.ge(ColumnUtil.field2ColumnName(field), startValue);
                                BeanUtil.setFieldValue(m, selectBetweenColumn.startValueColumn(), null);
                            }
                        }
                        if (StrUtil.isNotEmpty(selectBetweenColumn.endValueColumn())) {
                            endValue = BeanUtil.getFieldValue(m, selectBetweenColumn.endValueColumn());
                            if (endValue != null) {
                                queryWrapper.le(ColumnUtil.field2ColumnName(field), endValue);
                                BeanUtil.setFieldValue(m, selectBetweenColumn.endValueColumn(), null);
                            }
                        }

                        break;
                    default:
                        break;

                }
            }
            BeanUtil.setFieldValue(m, field.getName(), null);
        }
    }
}
