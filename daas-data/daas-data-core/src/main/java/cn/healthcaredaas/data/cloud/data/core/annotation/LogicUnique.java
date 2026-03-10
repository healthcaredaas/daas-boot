package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.*;

/**
 * @className: LogicUnique
 * @description: <p>逻辑唯一约束</p>
 * @author: chenpan
 * @date: 2021/11/22 18:54
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LogicUniques.class)
public @interface LogicUnique {

    String[] columns() default {};

    /**
     * 校验不通过消息提示
     */
    String message() default "已存在该字段!";

}
