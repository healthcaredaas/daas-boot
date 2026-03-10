package cn.healthcaredaas.data.cloud.data.oss.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**

 * @ClassName： MinioProperties.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/7 11:25
 * @Modify：
 */
@Data
@Configuration
@ConfigurationProperties("daas.minio")
public class MinioProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;

    private String bucket = "daas";

    private String defaultPath = "file";

    private Integer previewExpiry = 15 * 60;

    private String kkFileViewUrl;
}
