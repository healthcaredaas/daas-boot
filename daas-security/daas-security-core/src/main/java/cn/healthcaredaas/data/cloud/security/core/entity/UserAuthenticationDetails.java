package cn.healthcaredaas.data.cloud.security.core.entity;

import lombok.Data;

import java.util.Set;

/**

 * @ClassName： UserAuthenticationDetails.java
 * @Author： chenpan
 * @Date：2024/12/7 10:31
 * @Modify：
 */
@Data
public class UserAuthenticationDetails {

    private String userId;

    private String userName;

    private Set<String> roles;
}
