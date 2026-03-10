package cn.healthcaredaas.data.cloud.data.mybatisplus.configuration;

import cn.healthcaredaas.data.cloud.core.yaml.YamlPropertySourceFactory;
import cn.healthcaredaas.data.cloud.data.mybatisplus.enhance.BaseMetaObjectHandler;
import cn.healthcaredaas.data.cloud.data.mybatisplus.enhance.DaaSIdentifierGenerator;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**

 * @ClassName： MybatisPlusConfig.java
 * @Author： chenpan
 * @Date：2024/11/29 11:37
 * @Modify：
 */
@Configuration
@PropertySource(value = "classpath:/config/application-mybatis.yaml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public class MybatisPlusConfiguration {

    @Value("${spring.datasource.dbType:mysql}")
    private String dbType;

    private DbType parseDbType() {
        if (StringUtils.isNotBlank(dbType)) {
            DbType type = DbType.getDbType(dbType);
            if (ObjectUtils.isNotEmpty(type)) {
                return type;
            }
        }

        return DbType.MYSQL;
    }

    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件: PaginationInnerInterceptor
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(parseDbType());
        //防止全表更新与删除插件: BlockAttackInnerInterceptor
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }

    @Bean
    public IdentifierGenerator idGenerator() {
        return new DaaSIdentifierGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(BaseMetaObjectHandler.class)
    public BaseMetaObjectHandler mateMetaObjectHandler() {
        return new BaseMetaObjectHandler();
    }


    /**
     * IEnum 枚举配置
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return config -> config.setDefaultEnumTypeHandler(EnumTypeHandler.class);
    }

    /**
     * mybatis-plus 乐观锁拦截器
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }
}
