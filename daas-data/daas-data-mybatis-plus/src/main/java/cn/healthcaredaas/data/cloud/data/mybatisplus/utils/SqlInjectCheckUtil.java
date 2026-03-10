package cn.healthcaredaas.data.cloud.data.mybatisplus.utils;

import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**

 * @ClassName： SqlInjectCheckUtil.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/8 11:07
 * @Modify：
 */
@Slf4j
public class SqlInjectCheckUtil {

    /**
     * 检查字段是否在实体类中
     *
     * @param columns 字段列表
     * @param clazz   实体类
     */
    public static void checkColumnsInEntity(String[] columns, Class<?> clazz) {
        List<Field> allFields = TableInfoHelper.getAllFields(clazz);
        List<String> columnNames = allFields.stream()
                .map(field -> ColumnUtil.field2ColumnName(field).toLowerCase())
                .collect(Collectors.toList());

        for (String column : columns) {
            if (!columnNames.contains(column.toLowerCase())) {
                throw new RuntimeException("排序字段不存在:" + column);
            }
        }
    }

    public static void checkColumnsInEntity(String column, Class<?> clazz) {
        checkColumnsInEntity(new String[]{column}, clazz);

    }

}
