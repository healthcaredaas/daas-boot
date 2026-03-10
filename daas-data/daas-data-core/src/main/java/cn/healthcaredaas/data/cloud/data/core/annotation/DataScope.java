package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**

 * @ClassName： DataScope.java
 * @Author： chenpan
 * @Date：2024/1/4 15:16
 * @Modify：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    Scope scope() default Scope.ALL;

    String orgColumn() default "";

    String deptColumn() default "";

    String userColumn() default "createBy";

    /**
     * 数据权限级别
     */
    public static enum Scope {
        /**
         * 不限制
         */
        ALL,
        /**
         * 机构级限制
         */
        ORG,
        /**
         * 科室级限制
         */
        DEPT,
        /**
         * 用户级限制
         */
        USER;
    }
}
