package cn.healthcaredaas.data.cloud.web.core.password.codec;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**

 * @ClassName： PasswordUtil.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/5/9 16:19
 * @Modify：
 */
public class PasswordUtil {

    public static final String KEY_PASSWORD = "password";

    private static final SymmetricCrypto DES;

    static {
        DES = new SymmetricCrypto(SymmetricAlgorithm.DES, "0123456789ABHAEQ".getBytes());
    }

    public static String encode(String password) {
        return DES.encryptHex(password);
    }

    public static String decode(String encPassword) {
        String passwd;
        try {
            passwd = DES.decryptStr(encPassword);
            return StrUtil.isNotEmpty(passwd) ? passwd : encPassword;
        } catch (Exception e) {
            return encPassword;
        }
    }
}
