package cn.healthcaredaas.data.cloud.web.core.deserialize;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**

 * @ClassName： DateDeserializer.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/7/20 17:42
 * @Modify：
 */
public class DateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Date date = DateUtil.parse(jsonParser.getText());
        LocalDateTime time = LocalDateTimeUtil.of(date);
        return time != null ? time.toLocalDate() : null;
    }
}
