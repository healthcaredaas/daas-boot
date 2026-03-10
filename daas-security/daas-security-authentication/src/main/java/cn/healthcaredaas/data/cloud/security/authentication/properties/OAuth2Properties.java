package cn.healthcaredaas.data.cloud.security.authentication.properties;

/**
 * <pre>OAuth2 配置属性</pre>
 *
 * @ClassName： OAuth2Properties.java
 * @Author： chenpan
 * @Date：2024/12/1 16:39
 * @Modify：
 */

import cn.healthcaredaas.data.cloud.security.core.enums.Certificate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix ="daas.oauth2")
@Data
public class OAuth2Properties {

    private Jwk jwk = new Jwk();

    @Data
    public static class Jwk {

        private enum Strategy {
            STANDARD, CUSTOM
        }

        /**
         * 证书策略：standard OAuth2 标准证书模式；custom 自定义证书模式
         */
        private Certificate certificate = Certificate.CUSTOM;

        /**
         * jks证书文件路径
         * keytool -genkeypair -alias daas-cloud -keyalg RSA -keypass DaaS-Cloud -keystore daas-cloud.jks -storepass DaaS-Cloud
         * CN=healthcaredaas, OU=healthcaredaas, O=healthcaredaas, L=beijing, ST=beijing, C=cn
         * keytool -importkeystore -srckeystore daas-cloud.jks -destkeystore daas-cloud.jks -deststoretype pkcs12
         */
        private String jksKeyStore = "certificate/daas-cloud.jks";
        /**
         * jks证书密码
         */
        private String jksKeyPassword = "DaaS-Cloud";
        /**
         * jks证书密钥库密码
         */
        private String jksStorePassword = "DaaS-Cloud";
        /**
         * jks证书别名
         */
        private String jksKeyAlias = "daas-cloud";
    }
}
