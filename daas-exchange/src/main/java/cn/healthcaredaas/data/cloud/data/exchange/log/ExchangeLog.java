package cn.healthcaredaas.data.cloud.data.exchange.log;

import cn.healthcaredaas.data.cloud.data.exchange.enums.ExchangeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * @ClassName： ExchangeLog.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 14:52
 * @Modify：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeLog {

    private String code;

    private String name;

    private ExchangeType type;
}
