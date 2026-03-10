package cn.healthcaredaas.data.cloud.core.crypto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <pre>秘钥缓存存储实体</pre>
 *
 * @ClassName： SecretKey.java
 * @Author： chenpan
 * @Date：2024/12/1 17:31
 * @Modify：
 */
@Data
@NoArgsConstructor
public class SecretKey implements Serializable {

    /**
     * 数据存储身份标识
     */
    private String identity;
    /**
     * 对称加密算法秘钥
     */
    private String symmetricKey;

    /**
     * 服务器端非对称加密算法公钥
     * 1. RSA 为 Base64 格式
     * 2. SM2 为 Hex 格式
     */
    private String publicKey;

    /**
     * 服务器端非对称加密算法私钥
     */
    private String privateKey;

    /**
     * 本系统授权码模式中后台返回的 State
     */
    private String state;

    /**
     * 创建时间戳
     */
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
}
