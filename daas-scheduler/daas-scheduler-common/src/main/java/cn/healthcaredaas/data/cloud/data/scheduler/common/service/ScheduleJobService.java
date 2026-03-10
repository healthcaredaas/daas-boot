package cn.healthcaredaas.data.cloud.data.scheduler.common.service;

import cn.healthcaredaas.data.cloud.data.scheduler.common.dto.JobDTO;

import java.util.List;

/**

 * @ClassName： ScheduleJobService.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/19 14:58
 * @Modify：
 */
public interface ScheduleJobService<T extends JobDTO> {

    /**
     * 获取应用下所有任务
     *
     * @return
     */
    List<T> fetchAllJob();

    /**
     * 根据任务ID查找任务
     *
     * @param jobId
     * @return
     */
    T fetchJob(String jobId);

    /**
     * 创建/修改任务
     *
     * @param jobInfo
     * @return
     */
    String saveJob(T jobInfo);

    /**
     * 创建任务
     *
     * @param jobId
     * @param jobName
     * @param jobDescription
     * @param params
     * @param processorInfo
     * @param timeExpressionType
     * @param time
     * @return
     */
    String saveJob(String jobId, String jobName, String jobDescription, String params,
                   String processorInfo, Integer timeExpressionType, String time);

    /**
     * 创建cron任务
     *
     * @param jobId          任务ID，修改时必填
     * @param jobName        任务名称
     * @param jobDescription 任务描述
     * @param params         任务执行参数
     * @param processorInfo  任务处理器的全限定类名
     * @param cron           cron表达式
     * @return 任务ID
     */
    String saveCronJob(String jobId, String jobName, String jobDescription, String params,
                       String processorInfo, String cron);

    /**
     * 创建固定频率任务
     *
     * @param jobId          任务ID，修改时必填
     * @param jobName        任务名称
     * @param jobDescription 任务描述
     * @param params         任务执行参数
     * @param processorInfo  任务处理器的全限定类名
     * @param rate           固定频率，毫秒
     * @return 任务ID
     */
    String saveFixRateJob(String jobId, String jobName, String jobDescription, String params,
                          String processorInfo, Long rate);

    /**
     * 创建固定延迟任务
     *
     * @param jobId          任务ID，修改时必填
     * @param jobName        任务名称
     * @param jobDescription 任务描述
     * @param params         任务执行参数
     * @param processorInfo  任务处理器的全限定类名
     * @param delay          固定频率，毫秒
     * @return 任务ID
     */
    String saveFixDelayJob(String jobId, String jobName, String jobDescription, String params,
                           String processorInfo, Long delay);

    /**
     * 禁用任务
     *
     * @param jobId
     * @return
     */
    boolean disableJob(String jobId);

    /**
     * 启用任务
     *
     * @param jobId
     * @return
     */
    boolean enableJob(String jobId);

    /**
     * 删除任务
     *
     * @param jobId
     * @return
     */
    boolean deleteJob(String jobId);
}
