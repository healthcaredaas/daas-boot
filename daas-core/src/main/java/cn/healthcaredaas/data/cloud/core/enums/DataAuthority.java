package cn.healthcaredaas.data.cloud.core.enums;

import lombok.Getter;

/**

 * @ClassName： DataAuthority.java
 * @Description:
 * @Author： chenpan
 * @Date：2021/12/16 13:16
 * @Modify：
 */
public enum DataAuthority {

    /**
     * 所有数据权限
     */
    ALL("A00000"),
    /**
     * 机构级数据权限
     */
    ORG("A00001"),
    /**
     * 科室级数据权限
     */
    DEPT("A00002"),
    /**
     * 用户级数据权限
     */
    USER("A00003");

    @Getter
    private String code;


    DataAuthority(String code) {
        this.code = code;
    }
}
