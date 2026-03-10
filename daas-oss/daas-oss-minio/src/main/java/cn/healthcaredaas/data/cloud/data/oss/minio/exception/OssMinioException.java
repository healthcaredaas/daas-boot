package cn.healthcaredaas.data.cloud.data.oss.minio.exception;

import cn.healthcaredaas.data.cloud.core.exception.PlatformException;

/**

 * @ClassName： OssMinioException.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/7 11:40
 * @Modify：
 */
public class OssMinioException extends PlatformException {

    public OssMinioException() {
    }

    public OssMinioException(String message) {
        super(message);
    }

    public OssMinioException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssMinioException(Throwable cause) {
        super(cause);
    }

    public OssMinioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
