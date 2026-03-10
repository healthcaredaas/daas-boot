package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.*;

/**

 * @ClassName： RelationCondition.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/23 09:56
 * @Modify：
 */
@Inherited
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RelationCondition {

    /**
     * 关联字段
     *
     * @return
     */
    String field() default "id";

    /**
     * 关联外部字段
     *
     * @return
     */
    String relationField();
}
