package cn.healthcaredaas.data.cloud.core.feign;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**

 * @ClassName： FeignSign.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/31 10:32
 * @Modify：
 */
public class FeignSign {
    public static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI7wzZ6d1cqt8872BPr4t5A5jRxDE2Ej6WAbPF8VK+ZW8V+xQ4BdvaEZbBOMmcAn3hue0tWr551gz4F+2eYPnmUNqIxxGurWFAEQPYJ0qXF1WBJBXuPQUqGttunT8ScBvLfEn/9xuCZqUv81h3wdxCn72itmYhXxgGEgWOEr3SEfAgMBAAECgYBcZYXUgRwgPl96KYdAn6MwhDMV0LnnMep6KEDXCX1uEEdIXvJOOMoKJ7+3x0vfbSXiXsDUQ6GdFmYrw86gKvpsSZ8jqN9edSBiE1MNmtXdl9iEJhw72CWJIsexqLlCBUY31xRR7g1DVCyi7O7hcAPJrLPIEQ3n3/HcwakMHG1zuQJBANFWvO4q+ag2x4iPk0W1sZzBuA9sCB98dOjSWmCzpR+6TmVtXjqmaF6oeor5k5VpmGO1W6h9mUYquqQdi3l2ERUCQQCuzULzdTsTfMVil7uTp/MINiv8icnkTXlpIwSrgE1iTp6K4bhHbr9+RZ+gbvdEIHkd/mKpD/wo1+pNhT+Ix+5jAkEAiuZvs+RGBUxbtwv1HzMA589N7sWy2hl9hSJWH4XzkYrQC9K2zFmwNOa7cM1LFL+dAsPKTpEQC5O8hbqo2e3yRQJAV4ZhoqOta+mKr/5HpvxuXUs6pR2dIJqGU1V8bh81ICc7jVDOQ745VhGoht1pJAyaeKE6Z2qgmRbbs+snT+bD/wJAAhHnpJTnUFtso4mlatfN7TNC5Xb2kx2ubIpztU5E5360pyOXGP68QoJ78uXFDH25n9ZPbnSiBsGSFPbCuWau9w==";
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO8M2endXKrfPO9gT6+LeQOY0cQxNhI+lgGzxfFSvmVvFfsUOAXb2hGWwTjJnAJ94bntLVq+edYM+BftnmD55lDaiMcRrq1hQBED2CdKlxdVgSQV7j0FKhrbbp0/EnAby3xJ//cbgmalL/NYd8HcQp+9orZmIV8YBhIFjhK90hHwIDAQAB";

    public static Sign sign;

    static {
        sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, privateKey, publicKey);
    }

    private static byte[] sign(String data) {
        return sign.sign(data.getBytes());
    }

    public static String signData(String data) {
        return Base64.encode(sign(data));
    }

    public static boolean verifySign(String data, String plain) {
        return sign.verify(Base64.decode(data), sign(plain));
    }

    public static void main(String[] args) {
        KeyPair keyPair = SecureUtil.generateKeyPair("RSA");
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String privateKeyString = Base64.encode(privateKey.getEncoded());
        String publicKeyString = Base64.encode(publicKey.getEncoded());
        System.out.println("privateKey:" + privateKeyString);
        System.out.println("publicKey:" + publicKeyString);
    }
}
