package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: LikeColumn
 * @description: <p>模糊查询</p>
 * @author: chenpan
 * @date: 2021/11/20 15:50
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectLikeColumn {

    boolean value() default true;

    /**
     * 模糊查询通配符位置 before after both
     */
    WildcardPosition wildcardPosition() default WildcardPosition.AFTER;

    public static enum WildcardPosition {
        BEFORE,
        AFTER,
        BOTH;
    }
}
