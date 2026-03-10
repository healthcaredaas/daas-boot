package cn.healthcaredaas.data.cloud.data.mybatisplus.enhance;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * <pre>自动填充字段</pre>
 *
 * @ClassName： BaseMetaObjectHandler.java
 * @Author： chenpan
 * @Date：2024/11/29 11:46
 * @Modify：
 */
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateCount", Integer.class, 0);
        this.strictInsertFill(metaObject, "deleteFlag", String.class, "0");

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
