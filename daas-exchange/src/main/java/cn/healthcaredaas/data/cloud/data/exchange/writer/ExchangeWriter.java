package cn.healthcaredaas.data.cloud.data.exchange.writer;

import cn.hutool.core.map.MapUtil;
import cn.healthcaredaas.data.cloud.core.exception.BizException;
import cn.healthcaredaas.data.cloud.data.exchange.event.ExchangeEvent;
import cn.healthcaredaas.data.cloud.data.exchange.exception.NoDependsException;
import cn.healthcaredaas.data.cloud.data.exchange.exception.NotNullException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.HashMap;
import java.util.Map;

/**

 * @ClassName： ExchangeWriter.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/6/20 14:02
 * @Modify：
 */
public abstract class ExchangeWriter<Event extends ExchangeEvent> {

    private final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    private final ThreadLocal<Object> existHolder = new ThreadLocal<>();

    public static final String PARAMS_KEY_MESSAGE = "message";
    public static final String PARAMS_KEY_RETRY = "retry";

    @Getter
    @Setter
    private String topic;

    @Getter
    @Setter
    private int retryTimes = 5;

    /**
     * 初始化操作
     */
    public void init() {
        Map<String, Object> params = new HashMap<>(8);
        threadLocal.set(params);
    }

    /**
     * 字段校验
     *
     * @param event
     * @return
     */
    public boolean validate(Event event) {
        if (!event.isAdd() && !event.isUpdate() && !event.isDelete()) {
            throw new BizException("不支持的操作类型 actionCode:" + event.getActionCode());
        }
        return true;
    }

    /**
     * 前置依赖校验
     *
     * @param event
     * @return
     */
    public boolean preCheck(Event event) {
        return true;
    }

    public void post() {
        threadLocal.remove();
        existHolder.remove();
    }

    public void setExist(Object obj) {
        existHolder.set(obj);
    }

    public Object getExist() {
        return existHolder.get();
    }

    /**
     * 入库操作
     *
     * @param event 事件
     */
    public MutablePair<Boolean, String> write(Event event) {
        return null;
    }

    public String getMessage() {
        return MapUtil.getStr(threadLocal.get(), PARAMS_KEY_MESSAGE);
    }

    public void setMessage(String message) {
        threadLocal.get().put(PARAMS_KEY_MESSAGE, message);
    }

    public boolean isRetry() {
        return MapUtil.getBool(threadLocal.get(), PARAMS_KEY_RETRY);
    }

    public void setRetry(boolean retry) {
        threadLocal.get().put(PARAMS_KEY_RETRY, retry);
    }

    public void setRetry(boolean retry, String message, String topic) {
        threadLocal.get().put(PARAMS_KEY_RETRY, retry);
        threadLocal.get().put(PARAMS_KEY_MESSAGE, message);
        this.topic = topic;
    }


    /**
     * 数据交互整体操作
     *
     * @param event
     */
    public MutablePair<Boolean, String> operation(Event event) {
        MutablePair<Boolean, String> opResult = new MutablePair<>();
        init();
        if (!validate(event)) {
            throw new NotNullException(this.getMessage());
        }
        if (!preCheck(event)) {
            throw new NoDependsException(this.isRetry(), this.getRetryTimes(), this.getTopic(), this.getMessage());
        }
        try {
            opResult = write(event);
        } finally {
            post();
        }
        return opResult;
    }
}
