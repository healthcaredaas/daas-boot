package cn.healthcaredaas.data.cloud.security.authorization.cache;

import cn.hutool.core.map.MapUtil;
import cn.healthcaredaas.data.cloud.core.rest.RestResult;
import cn.healthcaredaas.data.cloud.security.authorization.remote.RemoteAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <pre>资源权限缓存</pre>
 *
 * @ClassName： AuthorityCache.java
 * @Description:
 * @Author： chenpan
 * @Date：2024/3/13 10:57
 * @Modify：
 */
@Service
@Slf4j
public class AuthorityCache {
    public static final String CACHE_AUTHORITY = "authority:apis";

    @Autowired
    private RemoteAuthService remoteAuthService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取角色权限并缓存
     *
     * @param role
     * @return
     */
    public Map<String, String> getAuthorities(String role) {
        Object value = redisTemplate.opsForHash().get(CACHE_AUTHORITY, role);
        if (value != null) {
            return (Map<String, String>) value;
        }
        RestResult<Map<String, String>> result = remoteAuthService.getRoleAuthorities(role);
        if (!result.getSuccess()) {
            log.error("远程权限获取异常:[{}]", result.getMessage());
            return null;
        } else {
            Map<String, String> authorities = result.getData();
            this.put(role, authorities);
            return authorities;
        }
    }

    /**
     * 缓存角色资源权限
     * authorities <url, method>
     *
     * @param role
     * @param authorities
     */
    public void put(String role, Map<String, String> authorities) {
        if (MapUtil.isNotEmpty(authorities)) {
            redisTemplate.opsForHash().put(CACHE_AUTHORITY, role, authorities);
        }
    }

    /**
     * 批量缓存角色资源权限
     * roleAuthorities <role, <url, method>>
     *
     * @param roleAuthorities
     */
    public void put(Map<String, Map<String, String>> roleAuthorities) {
        roleAuthorities.forEach(this::put);
    }

    public void clear() {
        redisTemplate.delete(CACHE_AUTHORITY);
    }

    public void delete(String role) {
        redisTemplate.opsForHash().delete(CACHE_AUTHORITY, role);
    }
}