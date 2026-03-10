package cn.healthcaredaas.data.cloud.security.core.enums;

/**

 * @ClassName： Certificate.java
 * @Author： chenpan
 * @Date：2024/12/1 16:43
 * @Modify：
 */
public enum Certificate {

    /**
     * Spring Authorization Server 默认的 JWK 生成方式
     */
    STANDARD,
    /**
     * 自定义证书 JWK 生成方式
     */
    CUSTOM;
}
