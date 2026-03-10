package cn.healthcaredaas.data.cloud.web.core.crypto.processor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>AES 加密算法处理器</pre>
 *
 * @ClassName： AESCryptoProcessor.java
 * @Author： chenpan
 * @Date：2024/12/1 17:19
 * @Modify：
 */
public class AESCryptoProcessor implements SymmetricCryptoProcessor {

    private static final Logger log = LoggerFactory.getLogger(AESCryptoProcessor.class);

    @Override
    public String createKey() {
        return RandomUtil.randomStringUpper(16);
    }

    @Override
    public String decrypt(String data, String key) {
        AES aes = SecureUtil.aes(StrUtil.utf8Bytes(key));
        byte[] result = aes.decrypt(Base64.decode(StrUtil.utf8Bytes(data)));
        log.trace("[DaaS] AES crypto decrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }

    @Override
    public String encrypt(String data, String key) {
        AES aes = SecureUtil.aes(StrUtil.utf8Bytes(key));
        byte[] result = aes.encrypt(StrUtil.utf8Bytes(data));
        log.trace("[DaaS] AES crypto encrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }
}