package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: InColumn
 * @description: <p>in查询</p>
 * @author: chenpan
 * @date: 2021/11/20 15:49
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectInColumn {

    boolean value() default true;

    /**
     * 数据库列取值
     *
     * @return
     */
    String column() default "";
}
