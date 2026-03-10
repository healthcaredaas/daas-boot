package cn.healthcaredaas.data.cloud.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**

 * @ClassName： IdUtils.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/15 15:06
 * @Modify：
 */
public class IdUtils {

    public static long nextId() {
        // 采用雪花算法获取id,时间回拨会存在重复,这里用随机数来减少重复的概率
        final Snowflake snowflake = IdUtil.getSnowflake(1, (int) (Math.random() * 20 + 1));
        return snowflake.nextId();
    }
}
