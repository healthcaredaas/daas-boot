package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>关联</pre>
 *
 * @ClassName： Relation.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/23 09:47
 * @Modify：
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {

    /**
     * 关联实体类
     *
     * @return
     */
    Class<?> entity();

    /**
     * 关联类的mapper
     *
     * @return
     */
    Class<?> relationMapper();

    /**
     * 关联类的关联字段
     *
     * @return
     */
    RelationCondition[] conditions() default {};
}
