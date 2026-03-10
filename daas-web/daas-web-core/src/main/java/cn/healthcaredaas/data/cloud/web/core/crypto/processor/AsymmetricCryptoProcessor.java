package cn.healthcaredaas.data.cloud.web.core.crypto.processor;


import cn.healthcaredaas.data.cloud.core.crypto.SecretKey;

/**
 * <pre>非对称加密</pre>
 *
 * @ClassName： AsymmetricCryptoProcessor.java
 * @Author： chenpan
 * @Date：2024/12/1 17:23
 * @Modify：
 */
public interface AsymmetricCryptoProcessor {

    /**
     * 创建非对称算法，公钥私钥对。
     *
     * @return 非对称算法，公钥私钥对
     */
    SecretKey createSecretKey();


    /**
     * 用私钥解密
     *
     * @param privateKey 非对称算法 KeyPair 私钥
     * @param content    待解密数据
     * @return 解密后的数据
     */
    String decrypt(String content, String privateKey);

    /**
     * 用公钥加密
     *
     * @param publicKey 非对称算法 KeyPair 公钥
     * @param content   待加密数据
     * @return 加密后的数据
     */
    String encrypt(String content, String publicKey);
}
