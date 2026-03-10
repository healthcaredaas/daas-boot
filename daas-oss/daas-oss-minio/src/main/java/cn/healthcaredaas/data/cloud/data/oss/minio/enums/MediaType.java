package cn.healthcaredaas.data.cloud.data.oss.minio.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @Description: TOOD
 * @Version: V1.0
 * @Author： chenpan
 * @Date：2024/9/22 10:19
 * @Modify：
 */
public enum MediaType {

    MP4("mp4", "video/mp4"),
    AVI("avi", "video/avi"),
    WEBM("webm", "video/webm"),
    WMV("wmv", "video/x-ms-wmv"),
    MKV("mkv", "video/x-matroska");

    @Getter
    public final String type;

    @Getter
    public final String contentType;

    MediaType(String type, String contentType) {
        this.type = type;
        this.contentType = contentType;
    }

    public static MediaType of(String type) {
        for (MediaType mediaType : MediaType.values()) {
            if (StrUtil.containsIgnoreCase(type, mediaType.type)) {
                return mediaType;
            }
        }
        return MP4;
    }
}
