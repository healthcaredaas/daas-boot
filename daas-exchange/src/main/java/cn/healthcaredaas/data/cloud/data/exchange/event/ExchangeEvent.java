package cn.healthcaredaas.data.cloud.data.exchange.event;

import cn.healthcaredaas.data.cloud.data.exchange.event.enums.Action;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**

 * @ClassName： ExchangeEvent.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 11:42
 * @Modify：
 */
@Data
public class ExchangeEvent {

    @JsonProperty(defaultValue = "0")
    private Action actionCode = Action.ADD;

    public boolean isAdd() {
        return actionCode == Action.ADD;
    }

    public boolean isUpdate() {
        return actionCode == Action.UPDATE;
    }

    public boolean isDelete() {
        return actionCode == Action.DELETE;
    }
}
