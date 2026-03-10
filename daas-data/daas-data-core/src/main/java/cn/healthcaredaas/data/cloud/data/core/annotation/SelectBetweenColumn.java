package cn.healthcaredaas.data.cloud.data.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: BetweenColumn
 * @description: <p>区间查询</p>
 * @author: chenpan
 * @date: 2021/11/20 15:52
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectBetweenColumn {

    boolean value() default true;

    Position position() default Position.NULL;

    /**
     * 开始取值的属性，为空时取本身
     *
     * @return
     */
    String startValueColumn() default "";

    /**
     * 结束取值的属性，为空时取本身
     *
     * @return
     */
    String endValueColumn() default "";

    /**
     * 开始还是结束
     */
    public static enum Position {
        NULL,
        START,
        END,
        START_AND_END;
    }
}
