package cn.healthcaredaas.data.cloud.data.exchange.log.service;

import cn.healthcaredaas.data.cloud.data.exchange.log.model.ExchangeApiLog;

/**

 * @ClassName： IExchangeApiLogService.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 14:46
 * @Modify：
 */
public interface IExchangeApiLogService {

    /**
     * 保存日志
     *
     * @param log
     */
    void saveLog(ExchangeApiLog log);
}
