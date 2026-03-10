package cn.healthcaredaas.data.cloud.data.mybatisplus.enhance;

import cn.healthcaredaas.data.cloud.core.utils.IdUtils;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**

 * @ClassName： DaaSIdentifierGenerator.java
 * @Author： chenpan
 * @Date：2024/11/29 11:47
 * @Modify：
 */
public class DaaSIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return IdUtils.nextId();
    }

    @Override
    public String nextUUID(Object entity) {
        return String.valueOf(IdUtils.nextId());
    }
}
