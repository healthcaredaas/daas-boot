package cn.healthcaredaas.data.cloud.data.scheduler.powerjob.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.powerjob.client.PowerJobClient;

import java.util.Arrays;

/**

 * @ClassName： PowerJobConfig.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/19 15:05
 * @Modify：
 */
@Configuration
@ConfigurationProperties(prefix = "powerjob.worker")
@Data
@Slf4j
public class PowerJobConfig {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用密码
     */
    private String password;

    /**
     * 调度服务器地址，IP:Port 或 域名，多值逗号分隔
     */
    private String serverAddress = "127.0.0.1:7700";

    /**
     * akka 工作端口，可选，默认 27777
     */
    private Integer akkaPort = 27777;


    /**
     * 持久化方式，可选，默认 disk
     */
    private String storeStrategy;

    @Bean
    public PowerJobClient powerJobClient() {
        try {
            return new PowerJobClient(Arrays.asList(serverAddress.split(",")), appName, password);
        }catch (Exception e){
            log.error("初始化powerjob客户端异常，请检查配置信息[powerjob.worker]");
            return null;
        }
    }
}
