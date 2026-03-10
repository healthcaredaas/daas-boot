package cn.healthcaredaas.data.cloud.data.oss.minio.controller;

import cn.healthcaredaas.data.cloud.core.exception.BizException;
import cn.healthcaredaas.data.cloud.data.oss.minio.enums.MediaType;
import cn.healthcaredaas.data.cloud.data.oss.minio.exception.OssMinioException;
import cn.hutool.core.io.IoUtil;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.data.oss.minio.service.MinioService;
import cn.healthcaredaas.data.cloud.web.core.annotation.Api;
import cn.healthcaredaas.data.cloud.web.core.annotation.DeleteOperation;
import cn.healthcaredaas.data.cloud.web.core.annotation.GetOperation;
import cn.healthcaredaas.data.cloud.web.core.annotation.PostOperation;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName： MinioController.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/7 14:44
 * @Modify：
 */
@Slf4j
@Api(value = "oss", name = "对象存储接口")
public class MinioController {
    public static final String RANGE_SPLIT = "-";

    @Autowired
    private MinioService minioService;

    @PostOperation(value = "upload", name = "上传文件")
    public RestResult<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || file.getSize() == 0) {
            return RestResult.failure("文件为空！");
        }
        String fileName = minioService.fileName(file);
        minioService.upload(fileName, file.getInputStream());
        return RestResult.success("上传成功", fileName);
    }

    @DeleteOperation(name = "删除文件")
    public RestResult<String> remove(@RequestParam("file") String fileName) {
        minioService.remove(fileName);
        return RestResult.success(fileName, "删除成功");
    }

    @GetOperation(value = "/onlinePreview", name = "在线预览地址")
    public RestResult<String> onlinePreviewUrl(@RequestParam("file") String fileName) {
        String url = minioService.onlineUrl(fileName);
        return RestResult.success("获取成功！", url);
    }

    @GetOperation(value = "/download", name = "下载文件")
    public void download(@RequestParam("file") String fileName, HttpServletResponse response, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        try (InputStream inputStream = minioService.download(fileName)) {
            //解决乱码
            if (userAgent.toUpperCase().contains("MSIE") ||
                    userAgent.contains("Trident/7.0")) {
                fileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8);
            } else {
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取异常", e);
            throw new BizException("文件读取异常");
        }
    }

    @GetOperation(value = "/video/play", name = "视频点播")
    public void videoPlay(@RequestParam("file") String fileName,
                          HttpServletRequest request,
                          HttpServletResponse response) throws OssMinioException {
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        long fileSize = minioService.fileSize(fileName);
        // 流起始位置
        long start = 0L;
        // 流结束位置
        long end = fileSize - 1;
        String rangeHeader = request.getHeader("Range");
        if (ObjectUtil.isNotEmpty(rangeHeader) && rangeHeader.startsWith("bytes=")) {
            // 断点续传 状态码206
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String numRang = request.getHeader("Range").replaceAll("bytes=", "");
            if (numRang.startsWith(RANGE_SPLIT)) {
                end = fileSize - 1;
                start = end - Long.parseLong(new String(numRang.getBytes(StandardCharsets.UTF_8), 1,
                        numRang.length() - 1)) + 1;
            } else if (numRang.endsWith(RANGE_SPLIT)) {
                start = Long.parseLong(new String(numRang.getBytes(StandardCharsets.UTF_8), 0,
                        numRang.length() - 1));
                end = fileSize - 1;
            } else {
                String[] strRange = numRang.split(RANGE_SPLIT);
                if (strRange.length == 2) {
                    start = Long.parseLong(strRange[0].trim());
                    end = Long.parseLong(strRange[1].trim());
                } else {
                    start = Long.parseLong(numRang.replaceAll(RANGE_SPLIT, "").trim());
                }
            }
            if (start < 0 || end < 0 || end >= fileSize || start > end) {
                response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }
        }
        MediaType mediaType = MediaType.of(fileType);
        if (mediaType == null) {
            throw new BizException("不支持的文件类型：" + fileType);
        }
        response.setContentType(mediaType.contentType);

        //response内容长度
        long responseContentLength = end - start + 1;
        try (InputStream inputStream = minioService.getStream(fileName, start, end)) {
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", String.format("bytes %d-%d/%d", start, end, fileSize));
            response.addHeader("Content-Length", String.valueOf(responseContentLength));

            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取异常", e);
            throw new BizException("文件读取异常");
        }
    }

}
