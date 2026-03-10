package cn.healthcaredaas.data.cloud.data.scheduler.common.dto;

import lombok.Data;

/**

 * @ClassName： JobDTO.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/19 15:01
 * @Modify：
 */
@Data
public class JobDTO {
    private String id;
    private String jobName;
    private String jobDescription;
    private String appId;
    private String jobParams;
    private Integer timeExpressionType;
    private String timeExpression;
    private Integer executeType;
    private Integer processorType;
    private String processorInfo;
}
