package cn.healthcaredaas.data.cloud.data.mybatisplus.wrapper;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.healthcaredaas.data.cloud.core.context.CurrentUserInfo;
import cn.healthcaredaas.data.cloud.core.context.UserContextHolder;
import cn.healthcaredaas.data.cloud.core.enums.DataAuthority;
import cn.healthcaredaas.data.cloud.core.utils.CheckUtils;
import cn.healthcaredaas.data.cloud.data.core.annotation.DataScope;
import cn.healthcaredaas.data.cloud.data.core.entity.BaseEntity;
import cn.healthcaredaas.data.cloud.data.core.utils.ColumnUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**

 * @ClassName： DataScopeWrapper.java
 * @Author： chenpan
 * @Date：2024/1/4 15:25
 * @Modify：
 */
public class DataScopeWrapper {
    public static <T extends BaseEntity> void wrapperDataScope(QueryWrapper<T> queryWrapper, T m) {
        DataScope dataScope = m.getClass().getAnnotation(DataScope.class);
        CurrentUserInfo user = getUser();
        switch (dataScope.scope()) {
            case ALL:
                break;
            case ORG:
                wrapperOrg(queryWrapper, m, dataScope, user);
                break;
            case DEPT:
                wrapperDept(queryWrapper, m, dataScope, user);
                break;
            default:
                wrapperUser(queryWrapper, m, dataScope, user);
                break;
        }
    }

    private static <T extends BaseEntity> void wrapperOrg(QueryWrapper<T> queryWrapper, T m, DataScope dataScope,
                                                          CurrentUserInfo user) {
        if (user.getDataAuthorities().contains(DataAuthority.ALL.getCode())) {
            return;
        }
        CheckUtils.notBlank(dataScope.orgColumn(), "缺少机构字段信息");
        queryWrapper.eq(ColumnUtil.field2ColumnName(ReflectUtil.getField(m.getClass(), dataScope.orgColumn())),
                user.getOrgCode());
    }

    private static <T extends BaseEntity> void wrapperDept(QueryWrapper<T> queryWrapper, T m, DataScope dataScope,
                                                           CurrentUserInfo user) {
        if (user.getDataAuthorities().contains(DataAuthority.ALL.getCode())) {
            return;
        }
        CheckUtils.notBlank(dataScope.orgColumn(), "缺少机构字段信息");
        if (user.getDataAuthorities().contains(DataAuthority.ORG.getCode())) {
            queryWrapper.eq(ColumnUtil.field2ColumnName(ReflectUtil.getField(m.getClass(), dataScope.orgColumn())),
                    user.getOrgCode());
            return;
        }
        CheckUtils.notBlank(dataScope.deptColumn(), "缺少科室字段信息");
        queryWrapper.eq(ColumnUtil.field2ColumnName(ReflectUtil.getField(m.getClass(), dataScope.orgColumn())),
                user.getOrgCode());
        queryWrapper.eq(ColumnUtil.field2ColumnName(ReflectUtil.getField(m.getClass(), dataScope.deptColumn())),
                user.getDeptCode());
    }

    private static <T extends BaseEntity> void wrapperUser(QueryWrapper<T> queryWrapper, T m, DataScope dataScope,
                                                           CurrentUserInfo user) {
        if (user.getDataAuthorities().contains(DataAuthority.ALL.getCode())) {
            return;
        }
        if (StrUtil.isNotBlank(dataScope.orgColumn()) && user.getDataAuthorities().contains(DataAuthority.ORG.getCode())) {
            queryWrapper.eq(ColumnUtil.field2ColumnName(m.getClass(), dataScope.orgColumn()),
                    user.getOrgCode());
            return;
        }
        if (StrUtil.isNotBlank(dataScope.deptColumn()) && user.getDataAuthorities().contains(DataAuthority.DEPT.getCode())) {
            queryWrapper.eq(ColumnUtil.field2ColumnName(m.getClass(), dataScope.orgColumn()),
                    user.getOrgCode());
            queryWrapper.eq(ColumnUtil.field2ColumnName(m.getClass(), dataScope.deptColumn()),
                    user.getDeptCode());
            return;
        }

        CheckUtils.notBlank(dataScope.userColumn(), "缺少用户字段信息");
        if (user.getDataAuthorities().contains(DataAuthority.USER.getCode())) {
            queryWrapper.eq(ColumnUtil.field2ColumnName(m.getClass(), dataScope.userColumn()),
                    user.getUserId());
        }
    }

    private static CurrentUserInfo getUser() {
        CurrentUserInfo userInfo = UserContextHolder.getCurrentUserInfo();
        CheckUtils.notNull(userInfo, "缺少当前用户信息");
        CheckUtils.notEmptyCollection(userInfo.getDataAuthorities(), "未分配数据权限");
        return userInfo;
    }
}
