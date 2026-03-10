package cn.healthcaredaas.data.cloud.web.rest.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**

 * @ClassName： OpenApiConfiguration.java
 * @Author： chenpan
 * @Date：2024/11/30 14:05
 * @Modify：
 */
@Slf4j
@Configuration
public class OpenApiConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI createOpenApi() {
        return new OpenAPI()
                .info(new Info().title("DaaS Cloud API")
                        .description("DaaS Cloud Microservices Architecture")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/")))
                .externalDocs(new ExternalDocumentation()
                        .description("DaaS Cloud Documentation")
                        .url(""));
    }
}