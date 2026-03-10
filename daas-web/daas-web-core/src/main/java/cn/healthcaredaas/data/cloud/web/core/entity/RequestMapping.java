package cn.healthcaredaas.data.cloud.web.core.entity;

import cn.healthcaredaas.data.cloud.core.entity.AbstractEntity;
import cn.healthcaredaas.data.cloud.web.core.enums.ResourceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <pre>rest请求资源信息</pre>
 *
 * @ClassName： RequestMapping.java
 * @Author： chenpan
 * @Date：2024/11/30 11:23
 * @Modify：
 */
@Data
public class RequestMapping extends AbstractEntity {

    @JsonProperty("authorityId")
    private String metadataId;

    @JsonProperty("authorityCode")
    private String metadataCode;

    @JsonProperty("authorityName")
    private String metadataName;

    private String requestMethod;

    private String serviceId;

    private String className;

    private String methodName;

    private String url;

    private String parentId;

    private String description;

    public RequestMapping() {
    }

    @JsonProperty("authorityType")
    private ResourceType resourceType = ResourceType.API;
}
