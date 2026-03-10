package cn.healthcaredaas.data.cloud.web.core.crypto.processor;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;

/**
 * <p>国密对称算法 SM4 处理器 </p>
 *
 */
public class SM4CryptoProcessor implements SymmetricCryptoProcessor {

    private static final Logger log = LoggerFactory.getLogger(SM4CryptoProcessor.class);

    @Override
    public String createKey() {
        SM4 sm4 = SmUtil.sm4();
        SecretKey secretKey = sm4.getSecretKey();
        byte[] encoded = secretKey.getEncoded();
        String result = HexUtil.encodeHexStr(encoded);
        log.trace("[DaaS] SM4 crypto create hex key, value is : [{}]", result);
        return result;
    }

    @Override
    public String decrypt(String data, String key) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        log.trace("[DaaS] SM4 crypto decrypt data [{}] with key : [{}]", data, key);
        String result = sm4.decryptStr(data);
        log.trace("[DaaS] SM4 crypto decrypt result is : [{}]", result);
        return result;
    }

    @Override
    public String encrypt(String data, String key) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        log.trace("[DaaS] SM4 crypto encrypt data [{}] with key : [{}]", data, key);
        String result = sm4.encryptHex(data);
        log.trace("[DaaS] SM4 crypto encrypt result is : [{}]", result);
        return result;
    }
}
