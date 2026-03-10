package cn.healthcaredaas.data.cloud.data.exchange.log.service.impl;

import cn.healthcaredaas.data.cloud.data.exchange.log.dao.ExchangeApiLogDao;
import cn.healthcaredaas.data.cloud.data.exchange.log.model.ExchangeApiLog;
import cn.healthcaredaas.data.cloud.data.exchange.log.service.IExchangeApiLogService;
import cn.healthcaredaas.data.cloud.data.mybatisplus.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**

 * @ClassName： ExchangeApiLogServiceImpl.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 14:47
 * @Modify：
 */
@Service
public class ExchangeApiLogServiceImpl extends BaseServiceImpl<ExchangeApiLogDao, ExchangeApiLog>
        implements IExchangeApiLogService {
    /**
     * 保存日志
     *
     * @param log
     */
    @Override
    public void saveLog(ExchangeApiLog log) {
        super.save(log);
    }
}
