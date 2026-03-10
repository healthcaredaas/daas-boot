package cn.healthcaredaas.data.cloud.security.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**

 * @ClassName： OAuth2GrantedAuthority.java
 * @Author： chenpan
 * @Date：2024/12/1 17:56
 * @Modify：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2GrantedAuthority implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
