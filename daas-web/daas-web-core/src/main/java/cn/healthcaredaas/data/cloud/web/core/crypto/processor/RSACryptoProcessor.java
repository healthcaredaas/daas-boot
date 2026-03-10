/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Engine Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/dante-engine
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.healthcaredaas.data.cloud.web.core.crypto.processor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.healthcaredaas.data.cloud.core.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * RSA 加密算法处理器
 */
public class RSACryptoProcessor implements AsymmetricCryptoProcessor {

    private static final Logger log = LoggerFactory.getLogger(RSACryptoProcessor.class);

    private static final String PKCS8_PUBLIC_KEY_BEGIN = "-----BEGIN PUBLIC KEY-----";
    private static final String PKCS8_PUBLIC_KEY_END = "-----END PUBLIC KEY-----";

    @Override
    public SecretKey createSecretKey() {
        RSA rsa = SecureUtil.rsa();
        SecretKey secretKey = new SecretKey();
        secretKey.setPrivateKey(rsa.getPrivateKeyBase64());
        secretKey.setPublicKey(appendPkcs8Padding(rsa.getPublicKeyBase64()));
        return secretKey;
    }

    /**
     * 去除 RSA Pkcs8Padding 中的标记格式 '-----BEGIN PUBLIC KEY-----' 和 '-----END PUBLIC KEY-----'，以及 '\n'
     *
     * @param key RSA Key
     * @return 清楚格式后的 RSA KEY
     */
    private String removePkcs8Padding(String key) {
        String result = StrUtil.replace(key, StrUtil.LF, StrUtil.EMPTY);
        String[] values = StrUtil.splitToArray(result, "-----");
        if (ArrayUtil.isNotEmpty(values)) {
            return values[1];
        }
        return key;
    }

    /**
     * 将 RSA PublicKey 转换为 Pkcs8Padding 格式。
     *
     * @param key RSA PublicKey
     * @return 转换为 Pkcs8Padding 格式的 RSA PublicKey
     */
    public String appendPkcs8Padding(String key) {
        return PKCS8_PUBLIC_KEY_BEGIN + StrUtil.LF + key + StrUtil.LF + PKCS8_PUBLIC_KEY_END;
    }

    @Override
    public String decrypt(String content, String privateKey) {
        byte[] base64Data = Base64.decode(content);
        RSA rsa = SecureUtil.rsa(privateKey, null);
        String result = StrUtil.utf8Str(rsa.decrypt(base64Data, KeyType.PrivateKey));
        log.trace("[DaaS] RSA crypto decrypt data, value is : [{}]", result);
        return result;
    }

    @Override
    public String encrypt(String content, String publicKey) {
        // 去除前端 RSA PublicKey中的 '-----BEGIN PUBLIC KEY-----'格式
        String key = removePkcs8Padding(publicKey);
        RSA rsa = SecureUtil.rsa(null, key);
        byte[] encryptedData = rsa.encrypt(content, KeyType.PublicKey);
        String result = Base64.encode(encryptedData);
        log.trace("[DaaS] RSA crypto decrypt data, value is : [{}]", result);
        return result;
    }
}
