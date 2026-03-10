package cn.healthcaredaas.data.cloud.web.rest.controller;


import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class VersionController {

    @Value("${spring.application.name:-}")
    private String application;

    @Value("${app.version:-}")
    private String version;

    /**
     * 打包时间
     */
    @Value("${app.build.time:-}")
    private String buildTime;

    @Value("${app.copyRight:-}")
    private String copyRight;

    @GetMapping
    public RestResult<Map<String, String>> version() {
        Map<String, String> ret = new HashMap<>(4);
        ret.put("application", application);
        ret.put("version", version);
        ret.put("buildTime", buildTime);
        ret.put("copyRight", copyRight);

        return RestResult.success("请求成功", ret);
    }
}
