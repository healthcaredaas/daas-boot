package cn.healthcaredaas.data.cloud.data.core.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.lang.reflect.Field;

/**

 * @ClassName： ColumnUtil.java
 * @Author： chenpan
 * @Date：2024/11/29 11:03
 * @Modify：
 */
public class ColumnUtil {

    public static String field2ColumnName(Field field) {
        TableField tableField = field.getAnnotation(TableField.class);
        TableId tableId = field.getAnnotation(TableId.class);
        if (ObjectUtil.isNotNull(tableField)) {
            return tableField.value();
        } else if (ObjectUtil.isNotNull(tableId)) {
            return tableId.value();
        }
        return field.getName();
    }

    public static String field2ColumnName(Class clazz, String fieldName) {
        String trimmedFieldName = StrUtil.trim(fieldName);
        Field field = ReflectUtil.getField(clazz, trimmedFieldName);
        if (ObjectUtil.isNull(field)) {
            return trimmedFieldName;
        }
        return ColumnUtil.field2ColumnName(ReflectUtil.getField(clazz, trimmedFieldName));
    }
}
