package cn.healthcaredaas.data.cloud.core.context;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**

 * @ClassName： CurrentUserInfo.java
 * @Author： chenpan
 * @Date：2024/11/30 15:19
 * @Modify：
 */
@Data
public class CurrentUserInfo {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否管理员
     */
    private boolean isAdmin;

    /**
     * 角色集合
     */
    private Set<String> authorities;

    /**
     * 系统权限标识集合
     */
    private Set<String> permissions;

    /**
     * 数据权限标识集合
     */
    private Set<String> dataAuthorities;

    /**
     * 科室ID集合
     */
    private List<String> deptIds;

    /**
     * 当前机构编码
     */
    private String orgCode;

    /**
     * 当前科室编码
     */
    private String deptCode;
}
