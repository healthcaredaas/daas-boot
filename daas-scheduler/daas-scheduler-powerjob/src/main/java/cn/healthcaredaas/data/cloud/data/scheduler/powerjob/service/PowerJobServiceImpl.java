package cn.healthcaredaas.data.cloud.data.scheduler.powerjob.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.healthcaredaas.data.cloud.data.scheduler.common.service.ScheduleJobService;
import cn.healthcaredaas.data.cloud.data.scheduler.powerjob.dto.PowerJobInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.powerjob.client.PowerJobClient;
import tech.powerjob.common.enums.ExecuteType;
import tech.powerjob.common.enums.ProcessorType;
import tech.powerjob.common.enums.TimeExpressionType;
import tech.powerjob.common.request.http.SaveJobInfoRequest;
import tech.powerjob.common.response.JobInfoDTO;
import tech.powerjob.common.response.ResultDTO;

import java.util.List;
import java.util.stream.Collectors;

/**

 * @ClassName： PowerJobServiceImpl.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/19 15:12
 * @Modify：
 */
@Service
public class PowerJobServiceImpl implements ScheduleJobService<PowerJobInfoDTO> {

    @Autowired(required = false)
    private PowerJobClient powerJobClient;

    /**
     * 获取应用下所有任务
     *
     * @return
     */
    @Override
    public List<PowerJobInfoDTO> fetchAllJob() {
        List<JobInfoDTO> jobs = powerJobClient.fetchAllJob().getData();
        if (CollectionUtil.isEmpty(jobs)) {
            return null;
        }
        return jobs.stream().map(PowerJobInfoDTO::toLocalDto).collect(Collectors.toList());
    }

    /**
     * 根据任务ID查找任务
     *
     * @param jobId
     * @return
     */
    @Override
    public PowerJobInfoDTO fetchJob(String jobId) {
        JobInfoDTO job = powerJobClient.fetchJob(Long.valueOf(jobId)).getData();
        return PowerJobInfoDTO.toLocalDto(job);
    }

    /**
     * 创建/修改任务
     *
     * @param jobInfo
     * @return
     */
    @Override
    public String saveJob(PowerJobInfoDTO jobInfo) {
        SaveJobInfoRequest request = new SaveJobInfoRequest();
        if (jobInfo.getId() != null) {
            request.setId(Long.valueOf(jobInfo.getId()));
        }
        //任务名称
        request.setJobName(jobInfo.getJobName());
        //任务描述
        request.setJobDescription(jobInfo.getJobDescription());
        //任务参数，Processor#process方法入参TaskContext对象的jobParams字段
        request.setJobParams(jobInfo.getJobParams());
        //时间表达式类型，枚举值
        request.setTimeExpressionType(TimeExpressionType.of(jobInfo.getTimeExpressionType()));
        //时间表达式，填写类型由timeExpressionType决定，比如CRON需要填写CRON表达式
        request.setTimeExpression(jobInfo.getTimeExpression());
        //执行类型，枚举值
        request.setExecuteType(ExecuteType.of(jobInfo.getExecuteType()));
        //处理器类型，枚举值
        request.setProcessorType(ProcessorType.of(jobInfo.getProcessorType()));
        //处理器参数，填写类型由processorType决定，如Java处理器需要填写全限定类名，如：com.github.kfcfans.oms.processors.demo.MapReduceProcessorDemo
        request.setProcessorInfo(jobInfo.getProcessorInfo());
        //最大实例数，该任务同时执行的数量（任务和实例就像是类和对象的关系，任务被调度执行后被称为实例）
        request.setMaxInstanceNum(jobInfo.getMaxInstanceNum());
        //单机线程并发数，表示该实例执行过程中每个Worker使用的线程数量
        request.setConcurrency(jobInfo.getConcurrency());
        //任务实例运行时间限制，0代表无任何限制，超时会被打断并判定为执行失败
        request.setInstanceTimeLimit(jobInfo.getInstanceTimeLimit());
        //任务实例重试次数，整个任务失败时重试，代价大，不推荐使用
        request.setMaxInstanceNum(jobInfo.getMaxInstanceNum());
        //Task重试次数，每个子Task失败后单独重试，代价小，推荐使用
        request.setTaskRetryNum(jobInfo.getTaskRetryNum());
        //最小可用CPU核心数，CPU可用核心数小于该值的Worker将不会执行该任务，0代表无任何限制
        request.setMinCpuCores(jobInfo.getMinCpuCores());
        //最小内存大小（GB），可用内存小于该值的Worker将不会执行该任务，0代表无任何限制
        request.setMinMemorySpace(jobInfo.getMinMemorySpace());
        //最小磁盘大小（GB），可用磁盘空间小于该值的Worker将不会执行该任务，0代表无任何限制
        request.setMinDiskSpace(jobInfo.getMinDiskSpace());
        //指定机器执行，设置该参数后只有列表中的机器允许执行该任务，空代表不指定机器
        request.setDesignatedWorkers(jobInfo.getDesignatedWorkers());
        //最大执行机器数量，限定调动执行的机器数量，0代表无限制
        request.setMaxWorkerCount(jobInfo.getMaxWorkerCount());
        //是否启用该任务，未启用的任务不会被调度
        request.setEnable(true);
        ResultDTO<Long> resultDTO = powerJobClient.saveJob(request);
        if (resultDTO.isSuccess()) {
            return String.valueOf(resultDTO.getData());
        } else {
            throw new RuntimeException(resultDTO.getMessage());
        }
    }

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
    @Override
    public String saveJob(String jobId, String jobName, String jobDescription, String params, String processorInfo, Integer timeExpressionType, String time) {
        PowerJobInfoDTO jobInfo = createJavaJobInfo(jobId, jobName, jobDescription, params, processorInfo, TimeExpressionType.of(timeExpressionType), time);
        return saveJob(jobInfo);
    }

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
    @Override
    public String saveCronJob(String jobId, String jobName, String jobDescription, String params, String processorInfo, String cron) {
        PowerJobInfoDTO jobInfo = createJavaJobInfo(jobId, jobName, jobDescription, params, processorInfo, TimeExpressionType.CRON, cron);
        return saveJob(jobInfo);
    }

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
    @Override
    public String saveFixRateJob(String jobId, String jobName, String jobDescription, String params, String processorInfo, Long rate) {
        PowerJobInfoDTO jobInfo = createJavaJobInfo(jobId, jobName, jobDescription, params, processorInfo, TimeExpressionType.FIXED_RATE, String.valueOf(rate));
        return saveJob(jobInfo);
    }

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
    @Override
    public String saveFixDelayJob(String jobId, String jobName, String jobDescription, String params, String processorInfo, Long delay) {
        PowerJobInfoDTO jobInfo = createJavaJobInfo(jobId, jobName, jobDescription, params, processorInfo, TimeExpressionType.FIXED_DELAY, String.valueOf(delay));
        return saveJob(jobInfo);
    }

    private PowerJobInfoDTO createJavaJobInfo(String jobId, String jobName, String jobDescription, String params, String processorInfo,
                                              TimeExpressionType expressionType, String timeExpression) {
        PowerJobInfoDTO jobInfo = new PowerJobInfoDTO();
        jobInfo.setId(jobId);
        jobInfo.setJobName(jobName);
        jobInfo.setJobDescription(jobDescription);
        jobInfo.setJobParams(params);
        jobInfo.setTimeExpressionType(expressionType.getV());
        jobInfo.setTimeExpression(timeExpression);
        jobInfo.setProcessorType(ProcessorType.BUILT_IN.getV());
        jobInfo.setProcessorInfo(processorInfo);
        jobInfo.setProcessorInfo(jobInfo.getProcessorInfo());

        jobInfo.setExecuteType(ExecuteType.STANDALONE.getV());
        //最大实例数，该任务同时执行的数量（任务和实例就像是类和对象的关系，任务被调度执行后被称为实例）
        jobInfo.setMaxInstanceNum(1);
        //单机线程并发数，表示该实例执行过程中每个Worker使用的线程数量
        jobInfo.setConcurrency(1);
        //任务实例运行时间限制，0代表无任何限制，超时会被打断并判定为执行失败
        jobInfo.setInstanceTimeLimit(0L);
        //任务实例重试次数，整个任务失败时重试，代价大，不推荐使用
        jobInfo.setMaxInstanceNum(0);
        //Task重试次数，每个子Task失败后单独重试，代价小，推荐使用
        jobInfo.setTaskRetryNum(2);
        //最小可用CPU核心数，CPU可用核心数小于该值的Worker将不会执行该任务，0代表无任何限制
        jobInfo.setMinCpuCores(0);
        //最小内存大小（GB），可用内存小于该值的Worker将不会执行该任务，0代表无任何限制
        jobInfo.setMinMemorySpace(0);
        //最小磁盘大小（GB），可用磁盘空间小于该值的Worker将不会执行该任务，0代表无任何限制
        jobInfo.setMinDiskSpace(0);
        //指定机器执行，设置该参数后只有列表中的机器允许执行该任务，空代表不指定机器
        jobInfo.setDesignatedWorkers(null);
        //最大执行机器数量，限定调动执行的机器数量，0代表无限制
        jobInfo.setMaxWorkerCount(1);

        return jobInfo;
    }

    /**
     * 禁用任务
     *
     * @param jobId
     * @return
     */
    @Override
    public boolean disableJob(String jobId) {
        return powerJobClient.disableJob(Long.valueOf(jobId)).isSuccess();
    }

    /**
     * 启用任务
     *
     * @param jobId
     * @return
     */
    @Override
    public boolean enableJob(String jobId) {
        return powerJobClient.enableJob(Long.valueOf(jobId)).isSuccess();
    }

    /**
     * 删除任务
     *
     * @param jobId
     * @return
     */
    @Override
    public boolean deleteJob(String jobId) {
        return powerJobClient.deleteJob(Long.valueOf(jobId)).isSuccess();
    }
}
