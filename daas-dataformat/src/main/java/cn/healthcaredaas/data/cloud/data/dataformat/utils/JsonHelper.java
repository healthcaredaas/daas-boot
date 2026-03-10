package cn.healthcaredaas.data.cloud.data.dataformat.utils;

import com.alibaba.fastjson2.JSON;

/**

 * @ClassName： JsonHelper.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/8/9 14:18
 * @Modify：
 */
public class JsonHelper {

    public static boolean isJson(String str) {
        try {
            JSON.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
