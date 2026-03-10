package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: DeleteOption
 * @description: <p>mybatis删除配置注解</p>
 * @author: chenpan
 * @date: 2021/6/29 10:07
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteOption {

    /**
     * 是否删除关联表数据
     *
     * @return
     */
    boolean deleteRelation() default true;

    /**
     * 关联关系
     *
     * @return
     */
    Relation[] relations() default {};
}
