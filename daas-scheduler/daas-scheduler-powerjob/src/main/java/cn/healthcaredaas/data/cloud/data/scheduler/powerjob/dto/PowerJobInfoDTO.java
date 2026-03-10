package cn.healthcaredaas.data.cloud.data.scheduler.powerjob.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.healthcaredaas.data.cloud.data.scheduler.common.dto.JobDTO;
import lombok.Data;
import tech.powerjob.common.model.AlarmConfig;
import tech.powerjob.common.model.LogConfig;
import tech.powerjob.common.response.JobInfoDTO;

import java.util.Date;

/**

 * @ClassName： PowerJobInfoDTO.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/19 15:15
 * @Modify：
 */
@Data
public class PowerJobInfoDTO extends JobDTO {

    private Integer maxInstanceNum;
    private Integer concurrency;
    private Long instanceTimeLimit;
    private Integer instanceRetryNum;
    private Integer taskRetryNum;
    private Integer status;
    private Long nextTriggerTime;
    private double minCpuCores;
    private double minMemorySpace;
    private double minDiskSpace;
    private String designatedWorkers;
    private Integer maxWorkerCount;
    private String notifyUserIds;
    private Date gmtCreate;
    private Date gmtModified;
    private String extra;
    private Integer dispatchStrategy;
    private String lifecycle;
    private AlarmConfig alarmConfig;
    private String tag;
    private LogConfig logConfig;


    public static PowerJobInfoDTO toLocalDto(JobInfoDTO dto) {
        if (dto == null) {
            return null;
        }
        PowerJobInfoDTO jobInfo = new PowerJobInfoDTO();
        BeanUtil.copyProperties(dto, jobInfo);
        jobInfo.setId(String.valueOf(dto.getId()));
        jobInfo.setAppId(String.valueOf(dto.getAppId()));
        return jobInfo;
    }

    public static JobInfoDTO toRemoteDto(PowerJobInfoDTO dto) {
        if (dto == null) {
            return null;
        }
        JobInfoDTO jobInfo = new JobInfoDTO();
        BeanUtil.copyProperties(dto, jobInfo);
        jobInfo.setId(Long.valueOf(dto.getId()));
        jobInfo.setAppId(Long.valueOf(dto.getAppId()));
        return jobInfo;
    }
}
