package cn.healthcaredaas.data.cloud.data.oss.minio.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**

 * @ClassName： MinioConfiguration.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/7 11:28
 * @Modify：
 */
@Data
@Configuration
@ConditionalOnProperty(prefix = "daas.minio", name = "enabled", havingValue = "true")
@Slf4j
public class MinioConfiguration {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        MinioClient client = null;
        try {
            client = MinioClient.builder()
                    .endpoint(minioProperties.getEndpoint())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();
            if (!client.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build())) {
                client.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidResponseException | ErrorResponseException |
                 InsufficientDataException | XmlParserException | InternalException | IOException | ServerException e) {
            log.error("创建Minio Bucket失败：{}", e.getMessage());
        }
        return client;
    }
}
