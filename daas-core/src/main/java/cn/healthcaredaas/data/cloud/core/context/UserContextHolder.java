package cn.healthcaredaas.data.cloud.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**

 * @ClassName： UserContextHolder.java
 * @Author： chenpan
 * @Date：2024/11/30 15:18
 * @Modify：
 */
public class UserContextHolder {

    private static final ThreadLocal<CurrentUserInfo> currentUserInfo = new TransmittableThreadLocal<>();

    public static void clear() {
        currentUserInfo.remove();
    }

    public static CurrentUserInfo getCurrentUserInfo() {
        return currentUserInfo.get();
    }

    public static void setCurrentUserInfo(CurrentUserInfo userInfo) {
        currentUserInfo.set(userInfo);
    }
}
