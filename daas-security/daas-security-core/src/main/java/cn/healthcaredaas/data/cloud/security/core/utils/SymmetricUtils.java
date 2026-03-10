package cn.healthcaredaas.data.cloud.security.core.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.healthcaredaas.data.cloud.security.core.exception.IllegalSymmetricKeyException;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>Aes加解密工具</pre>
 *
 * @ClassName： SymmetricUtils.java
 * @Author： chenpan
 * @Date：2024/12/1 14:01
 * @Modify：
 */
@Slf4j
public class SymmetricUtils {

    private static String encryptedRealSecretKey(String symmetricKey) {
        String realSecretKey = RandomUtil.randomString(16);
        log.trace("[DaaS] Generate Random Secret Key is : [{}]", realSecretKey);

        AES ase = SecureUtil.aes(symmetricKey.getBytes());
        String encryptedRealSecretKey = ase.encryptHex(realSecretKey);
        log.trace("[DaaS] Generate Encrypt Hex Secret Key is : [{}]", encryptedRealSecretKey);

        return encryptedRealSecretKey;
    }

    public static String getEncryptedSymmetricKey() {
        String symmetricKey = RandomUtil.randomString(16);
        String realSecretKey = encryptedRealSecretKey(symmetricKey);
        log.trace("[DaaS] Generate Symmetric Key is : [{}]", realSecretKey);

        return symmetricKey +
                StrUtil.SLASH +
                realSecretKey;
    }

    public static byte[] getDecryptedSymmetricKey(String key) {
        if (!StrUtil.contains(key, StrUtil.SLASH)) {
            throw new IllegalSymmetricKeyException("Parameter Illegal!");
        }

        String[] keys = StrUtil.splitToArray(key, StrUtil.SLASH);
        String symmetricKey = keys[0];
        String realSecretKey = keys[1];

        AES ase = SecureUtil.aes(symmetricKey.getBytes());
        return ase.decrypt(realSecretKey);
    }

    public static String decrypt(String content, byte[] key) {
        if (ArrayUtil.isNotEmpty(key)) {
            AES ase = SecureUtil.aes(key);
            return ase.decryptStr(content);
        }

        return "";
    }
}
