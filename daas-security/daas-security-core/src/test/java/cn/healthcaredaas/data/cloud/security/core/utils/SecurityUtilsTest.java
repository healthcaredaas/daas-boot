package cn.healthcaredaas.data.cloud.security.core.utils;

import org.junit.jupiter.api.Test;

/**

 * @ClassName： SecurityUtilsTest.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/10 10:50
 * @Modify：
 */
class SecurityUtilsTest {

    @Test
    void encrypt() {
        String s = "123456";
        System.out.println(SecurityUtils.encrypt(s));
    }
}