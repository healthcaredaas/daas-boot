package cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.data.core.annotation.DataScope;
import cn.healthcaredaas.data.cloud.data.core.annotation.EnableSelectOption;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import cn.healthcaredaas.data.cloud.data.mybatisplus.utils.SqlInjectCheckUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author chenpan
 * @date 2021/1/19 12:14
 */
public class BaseCRUDWrapper<T extends BaseEntity> {

    public static final String ORDER_DESC_STR = " DESC";
    public static final String ORDER_ASC_STR = " ASC";

    public QueryWrapper<T> orderBy(QueryWrapper<T> queryWrapper, String[] sortBy, Class<?> clazz) {
        if (ArrayUtil.isNotEmpty(sortBy)) {
            List<String> descColumns = new ArrayList<>();
            List<String> ascColumns = new ArrayList<>();
            for (String column : sortBy) {
                String trimStr = StrUtil.trim(column);
                String fieldName = null;
                if (StrUtil.endWithIgnoreCase(StrUtil.trimEnd(column), ORDER_DESC_STR)) {
                    fieldName = StrUtil.replaceIgnoreCase(trimStr, ORDER_DESC_STR, StrUtil.EMPTY);
                    descColumns.add(ColumnUtil.field2ColumnName(clazz, fieldName));
                } else {
                    fieldName = StrUtil.replaceIgnoreCase(trimStr, ORDER_ASC_STR, StrUtil.EMPTY);
                    ascColumns.add(ColumnUtil.field2ColumnName(clazz, fieldName));
                }
            }
            if (CollectionUtil.isNotEmpty(descColumns) || CollectionUtil.isNotEmpty(ascColumns)) {
                String[] columns = Stream.of(descColumns, ascColumns).flatMap(Collection::stream).toArray(String[]::new);
                // 注入检查
                SqlInjectCheckUtil.checkColumnsInEntity(columns, clazz);

                queryWrapper.orderByDesc(descColumns);
                queryWrapper.orderByAsc(ascColumns);
            }
        }
        return queryWrapper;
    }

    public QueryWrapper<T> orderBy(QueryWrapper<T> queryWrapper, T m) {
        return orderBy(queryWrapper, m.getSortBy(), m.getClass());
    }

    public QueryWrapper<T> query(T m) {

        QueryWrapper<T> queryWrapper = new QueryWrapper<>(m);
        return orderBy(queryWrapper, m);
    }

    public QueryWrapper<T> baseQueryWrapper(T m) throws IllegalAccessException {

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (m.getClass().isAnnotationPresent(DataScope.class)) {
            DataScopeWrapper.wrapperDataScope(queryWrapper, m);
        }
        //查询配置
        if (m.getClass().isAnnotationPresent(EnableSelectOption.class)) {
            Field[] fields = m.getClass().getDeclaredFields();
            for (Field field : fields) {
                LikeColumnWrapper.likeField(queryWrapper, m, field);
                BetweenColumnWrapper.betweenField(queryWrapper, m, field);
                InColumnWrapper.inField(queryWrapper, m, field);
            }
        }

        queryWrapper.setEntity(trimAndNullStrFields(m));
        return orderBy(queryWrapper, m);
    }

    private T trimAndNullStrFields(T bean, String... ignoreFields) {
        return BeanUtil.edit(bean, (field) -> {
            if (ignoreFields != null && ArrayUtil.containsIgnoreCase(ignoreFields, field.getName())) {
                // 不处理忽略的Fields
                return field;
            }
            if (String.class.equals(field.getType())) {
                // 只有String的Field才处理
                final String val = (String) ReflectUtil.getFieldValue(bean, field);
                if (null != val) {
                    final String trimVal = StrUtil.trim(val);
                    if (StrUtil.isEmptyOrUndefined(trimVal)) {
                        ReflectUtil.setFieldValue(bean, field, null);
                    } else {
                        if (!val.equals(trimVal)) {
                            // Field Value不为null，且首尾有空格才处理
                            ReflectUtil.setFieldValue(bean, field, trimVal);
                        }
                    }
                }
            }
            return field;
        });
    }

}
