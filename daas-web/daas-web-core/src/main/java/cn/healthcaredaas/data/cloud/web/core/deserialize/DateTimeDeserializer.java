package cn.healthcaredaas.data.cloud.web.core.deserialize;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**

 * @ClassName： DateTimeDeserializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/20 17:42
 * @Modify：
 */
public class DateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Date date = DateUtil.parse(jsonParser.getText());
        return LocalDateTimeUtil.of(date);
    }
}
