package cn.healthcaredaas.data.cloud.data.exchange.log.dao;

import cn.healthcaredaas.data.cloud.data.exchange.log.model.ExchangeApiLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**

 * @ClassName： ExchangeApiLogDao.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 14:46
 * @Modify：
 */
@Mapper
public interface ExchangeApiLogDao extends BaseMapper<ExchangeApiLog> {
}
