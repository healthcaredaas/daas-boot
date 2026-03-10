package cn.healthcaredaas.data.cloud.web.core.configuration;

import cn.healthcaredaas.data.cloud.web.core.converter.EnableStatusEnumConverter;
import cn.healthcaredaas.data.cloud.web.core.converter.LocalDateTimeConverter;
import cn.healthcaredaas.data.cloud.web.core.converter.SuccessStatusEnumConverter;
import cn.healthcaredaas.data.cloud.web.core.converter.BaseEnumConverterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**

 * @ClassName： WebMvcConfig.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/9 15:12
 * @Modify：
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

    @Bean
    public BaseEnumConverterFactory enumConverterFactory() {
        return new BaseEnumConverterFactory();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.debug("add registry……");
        registry.addConverter(new LocalDateTimeConverter());
        registry.addConverter(new EnableStatusEnumConverter());
        registry.addConverter(new SuccessStatusEnumConverter());
        registry.addConverterFactory(enumConverterFactory());
    }
}
