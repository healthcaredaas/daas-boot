package cn.healthcaredaas.data.cloud.data.oss.minio.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.healthcaredaas.data.cloud.data.oss.minio.config.MinioProperties;
import cn.healthcaredaas.data.cloud.data.oss.minio.exception.OssMinioException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName： MinioService.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/7 11:41
 * @Modify：
 */
@Slf4j
@Component
public class MinioService {

    @Autowired(required = false)
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    public String fileName(MultipartFile file) throws IOException {
        String md5 = SecureUtil.md5(file.getInputStream());
        return StrUtil.join(StrUtil.SLASH, minioProperties.getDefaultPath(), md5)
                + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
    }

    public void upload(String name, InputStream stream) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .stream(stream, stream.available(), -1)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new OssMinioException("上传失败" + e.getMessage(), e);
        }
    }

    public void upload(String name, InputStream stream, String contentType) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .stream(stream, stream.available(), -1)
                    .contentType(contentType)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new OssMinioException("上传失败" + e.getMessage(), e);
        }
    }

    public void remove(String name) {
        try {
            RemoveObjectArgs objectArgs = RemoveObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .build();
            minioClient.removeObject(objectArgs);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new OssMinioException("删除文件失败" + e.getMessage(), e);
        }
    }

    public String onlineUrl(String name) {
        try {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .expiry(minioProperties.getPreviewExpiry(), TimeUnit.SECONDS)
                    .build();
            return minioClient.getPresignedObjectUrl(args);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new OssMinioException("获取文件在线地址失败" + e.getMessage(), e);
        }
    }

    public InputStream download(String name) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .build();
            return minioClient.getObject(args);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new OssMinioException("获取文件流失败" + e.getMessage(), e);
        }
    }

    public InputStream getStream(String name, long start, long end) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .offset(start)
                    .length(end)
                    .build();
            return minioClient.getObject(args);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new OssMinioException("获取文件流失败" + e.getMessage(), e);
        }
    }

    public long fileSize(String name) {
        try {
            StatObjectArgs args = StatObjectArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .object(name)
                    .build();
            return minioClient.statObject(args).size();
        } catch (ServerException | InvalidResponseException | XmlParserException | InternalException |
                 InvalidKeyException | NoSuchAlgorithmException | ErrorResponseException | InsufficientDataException |
                 IOException e) {
            throw new OssMinioException("获取文件大小失败" + e.getMessage(), e);
        }
    }
}
