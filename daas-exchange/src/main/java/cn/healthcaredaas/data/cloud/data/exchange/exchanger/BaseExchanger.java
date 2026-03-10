package cn.healthcaredaas.data.cloud.data.exchange.exchanger;

import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import org.apache.commons.lang3.tuple.MutablePair;

/**

 * @ClassName： BaseExchanger.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/17 16:15
 * @Modify：
 */
public interface BaseExchanger {

    /**
     * 写操作接口返回值
     *
     * @param result
     * @return
     */
    default RestResult<String> writeResult(MutablePair<Boolean, String> result) {
        return result.getLeft() ? RestResult.success(result.getRight()) : RestResult.failure(result.getRight());
    }
}
